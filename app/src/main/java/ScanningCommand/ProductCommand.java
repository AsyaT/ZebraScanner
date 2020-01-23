package ScanningCommand;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.ScannerException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import businesslogic.ApplicationException;
import businesslogic.BarcodeTypes;
import businesslogic.FullDataTableControl;
import businesslogic.ListViewPresentationModel;
import businesslogic.ProductLogic;
import businesslogic.ProductStructureModel;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScannerApplication;

public class ProductCommand implements Command {

    Activity Activity;
    Scanner CurrentScanner;
    ScannerApplication appState;

    MediaPlayer mediaPlayer;

    ProductLogic ProductLogic;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;
        this.CurrentScanner = ((MainActivity)activity).getScanner();

        appState = ((ScannerApplication) Activity.getApplication());

        this.ProductLogic = new ProductLogic(appState.barcodeStructureModel,
                appState.nomenclatureStructureModel,
                appState.characterisiticStructureModel,
                appState.manufacturerStructureModel,
                appState.baseDocumentStructureModel,
                appState.LocationContext);

        mediaPlayer = MediaPlayer.create(Activity, R.raw.beep01);
    }

    protected void SelectionDialog(List<ProductStructureModel> listNomenclature)
    {
        List<CharSequence> nomenclatures = new ArrayList<CharSequence>();
        final ProductStructureModel[] result = {null};

        for(ProductStructureModel nomenclature : listNomenclature)
        {
            nomenclatures.add(
                    appState.nomenclatureStructureModel.FindProductByGuid( nomenclature.GetProductGuid())+
                            "\n Характеристика: "+ appState.characterisiticStructureModel.FindCharacteristicByGuid(nomenclature.GetCharacteristicGUID())+
                            "\n Вес: "+nomenclature.GetWeight().toString()+"\n\n");
        }

        CharSequence[] showedNomenclatures = nomenclatures.toArray(new CharSequence[nomenclatures.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.Activity);

        this.Activity.runOnUiThread(new Runnable() {
            public void run() {

                builder.setTitle(R.string.PleaseChoseNomenclature)
                        .setItems(showedNomenclatures, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                result[0] = listNomenclature.get(i);

                                try
                                {
                                    ProductLogic.IsExistsInOrder(result[0]);
                                    SuccessSaveData(result[0]);
                                }
                                catch (ApplicationException ex)
                                {
                                    ((MainActivity)Activity).AlarmAndNotify(ex.getMessage());
                                }
                                finally
                                {
                                    PostAction();

                                    try {
                                        CurrentScanner.enable();
                                        CurrentScanner.read();
                                    } catch (ScannerException e) {
                                        e.printStackTrace();
                                    }

                                    dialogInterface.dismiss();
                                }
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                try {
                    CurrentScanner.disable();
                } catch (ScannerException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();

            }});

    }


    //TODO: code duplication
    protected void SuccessSaveData( ProductStructureModel product)
    {
        ListViewPresentationModel viewUpdateModel = this.ProductLogic.CreateListView(product);

        if(((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed == false)
        {
            ((MainActivity)this.Activity).new BaseAsyncDataUpdate( viewUpdateModel).execute();
        }
        else if(((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed == true)
        {
            String result = this.ProductLogic.CreateStringResponse(product);
            ((MainActivity)this.Activity).new AsyncBarcodeInfoUpdate().execute(result);
        }

        FullDataTableControl.Details detailsModel = this.ProductLogic.CreateDetails(product);
        appState.ScannedProductsToSend.Add(detailsModel);
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data)
    {
        try {
            this.ProductLogic.IsAllowedToScan(data.getLabelType());
        }
        catch (ApplicationException ex)
        {
            ((MainActivity)Activity).AlarmAndNotify(ex.getMessage());
        }
        finally
        {
            ArrayList<ProductStructureModel> products = null;
            try
            {
                products = this.ProductLogic.CreateProducts(data.getData(), BarcodeTypes.GetType(data.getLabelType()));
            }
            catch (ApplicationException ex)
            {
                ((MainActivity)Activity).AlarmAndNotify(ex.getMessage());
            } catch (ParseException e) {
                ((MainActivity)Activity).AlarmAndNotify(e.getMessage());
            }
            finally
            {
                if(products!=null && products.size()>1)
                {
                    SelectionDialog(products);
                }
                else
                {
                    try
                    {
                        this.ProductLogic.IsExistsInOrder(products.get(0));
                        SuccessSaveData(products.get(0));
                    }
                    catch (ApplicationException ex)
                    {
                        ((MainActivity)Activity).AlarmAndNotify(ex.getMessage());
                    }

                }
            }
        }

    }


    @Override
    public void PostAction() {
    }
}

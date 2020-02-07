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
import businesslogic.BarcodeProductLogic;
import businesslogic.BarcodeScanningLogic;
import businesslogic.BarcodeTypes;
import businesslogic.BaseDocumentLogic;
import businesslogic.DoesNotExistsInOrderException;
import businesslogic.FullDataTableControl;
import businesslogic.ListViewPresentationModel;
import businesslogic.ProductLogic;
import businesslogic.ProductStructureModel;
import businesslogic.ScannerState;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScannerApplication;

public class ProductCommand implements Command {

    Activity Activity = null;
    Scanner CurrentScanner = null;
    ScannerApplication appState = null;

    MediaPlayer mediaPlayer = null;

    businesslogic.BarcodeProductLogic BarcodeProductLogic;
    businesslogic.ProductLogic ProductLogic;
    BarcodeScanningLogic barcodeScanningLogic;
    BaseDocumentLogic baseDocumentLogic;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;
        this.CurrentScanner = ((MainActivity) activity).getScanner();

        appState = ((ScannerApplication) Activity.getApplication());

        this.BarcodeProductLogic = new BarcodeProductLogic(appState.barcodeStructureModel,
                appState.nomenclatureStructureModel,
                appState.characterisiticStructureModel,
                appState.manufacturerStructureModel);

        this.ProductLogic = new ProductLogic(
                appState.nomenclatureStructureModel,
                appState.characterisiticStructureModel,
                appState.manufacturerStructureModel
        );

        this.barcodeScanningLogic = new BarcodeScanningLogic(appState.LocationContext, appState.baseDocumentStructureModel);
        this.baseDocumentLogic = new BaseDocumentLogic(appState.baseDocumentStructureModel);

        mediaPlayer = MediaPlayer.create(Activity, R.raw.beep01);
    }

    protected void SelectionDialog(List<ProductStructureModel> listNomenclature) {
        List<CharSequence> nomenclatures = new ArrayList<CharSequence>();
        final ProductStructureModel[] result = {null};

        for (ProductStructureModel nomenclature : listNomenclature) {
            nomenclatures.add(
                    appState.nomenclatureStructureModel.FindProductByGuid(nomenclature.GetProductGuid()) +
                            "\n Характеристика: " + appState.characterisiticStructureModel.FindCharacteristicByGuid(nomenclature.GetCharacteristicGUID()) +
                            "\n Вес: " + nomenclature.GetWeight().toString() + "\n\n");
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

                                try {

                                    SuccessSaveData(CheckInOrder(result[0]));

                                } catch (DoesNotExistsInOrderException ex) {
                                    ((MainActivity) Activity).AlarmAndNotify(ex.getMessage());
                                } finally {

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

            }
        });

    }

    protected void SuccessSaveData(ProductStructureModel product) {
        if (((MainActivity) this.Activity).IsBarcodeInfoFragmentShowed == false)
        {
            ListViewPresentationModel viewUpdateModel = this.ProductLogic.CreateListView(product);

            ((MainActivity) this.Activity).new BaseAsyncDataUpdate(viewUpdateModel).execute();

            FullDataTableControl.Details detailsModel = this.ProductLogic.CreateDetails(product);
            appState.ScannedProductsToSend.Add(detailsModel);
        }
        else if (((MainActivity) this.Activity).IsBarcodeInfoFragmentShowed == true)
        {
            String result = this.BarcodeProductLogic.CreateStringResponse(product);
            ((MainActivity) this.Activity).new AsyncBarcodeInfoUpdate().execute(result);
        }
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data) {
        try {
            ParseAction(data.getData(), BarcodeTypes.GetType(data.getLabelType()));
        } catch (ApplicationException ex) {
            ((MainActivity) Activity).AlarmAndNotify(ex.getMessage());
        } catch (ParseException e) {
            ((MainActivity) Activity).AlarmAndNotify(e.getMessage());
        } catch (DoesNotExistsInOrderException e) {
            ((MainActivity) Activity).AlarmAndNotify(e.getMessage());
        }
    }

    public ProductStructureModel ParseAction(String barcodeData, BarcodeTypes barcodeType) throws ApplicationException, ParseException, DoesNotExistsInOrderException {
            this.barcodeScanningLogic.IsBarcodeTypeAllowedToScan(barcodeType);
            this.barcodeScanningLogic.IsBarcodeAllowedToScan(ScannerState.PRODUCT); // We are in Product command, so State = Product scanning

            ArrayList<ProductStructureModel> products =
                    this.BarcodeProductLogic.FindProductByBarcode(barcodeData, barcodeType);

            if (products.size() > 1)
            {
                SelectionDialog(products);
            }
            else
            {
                return CheckInOrder(products.get(0));

            }

        return null;
    }

    private ProductStructureModel CheckInOrder(ProductStructureModel product) throws DoesNotExistsInOrderException {
        if (this.baseDocumentLogic.IsBaseDocumentScanned())
        {
            this.baseDocumentLogic.IsExistsInOrder(product);
        }
        return product;
    }

}

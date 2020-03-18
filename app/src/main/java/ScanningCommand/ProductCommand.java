package scanningcommand;

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
import businesslogic.ResponseModelGenerator;
import businesslogic.ScannerState;
import models.ObjectForSaving;
import models.ProductStructureModel;
import models.ScanningBarcodeStructureModel;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScannerApplication;

public class ProductCommand extends ResponseFormat implements Command
{

    Activity Activity = null;
    Scanner CurrentScanner = null;
    ScannerApplication appState = null;

    MediaPlayer mediaPlayer = null;

    businesslogic.BarcodeProductLogic BarcodeProductLogic;
    ResponseModelGenerator responseModelGenerator;
    BarcodeScanningLogic barcodeScanningLogic;
    BaseDocumentLogic baseDocumentLogic;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;
        this.CurrentScanner = ((MainActivity) activity).getScanner();

        appState = ((ScannerApplication) Activity.getApplication());

        this.BarcodeProductLogic = new BarcodeProductLogic(appState.barcodeStructureModel);

        this.responseModelGenerator = new ResponseModelGenerator(
                appState.nomenclatureStructureModel,
                appState.characteristicStructureModel,
                appState.manufacturerStructureModel
        );

        try {
            this.barcodeScanningLogic = new BarcodeScanningLogic(appState.GetLocationContext(), appState.GetBaseDocument());
            this.baseDocumentLogic = new BaseDocumentLogic(appState.GetBaseDocument());
        }
        catch (ApplicationException ex)
        {
            ((MainActivity) Activity).AlarmAndNotify(ex.getMessage());
        }
        mediaPlayer = MediaPlayer.create(Activity, R.raw.beep01);
    }

    protected void SelectionDialog(List<ProductStructureModel> listNomenclature, ScanningBarcodeStructureModel barcode) {
        List<CharSequence> nomenclatures = new ArrayList<CharSequence>();
        final ProductStructureModel[] result = {null};

        try {
            for (ProductStructureModel nomenclature : listNomenclature) {
                nomenclatures.add(
                        appState.nomenclatureStructureModel.FindProductByGuid(nomenclature.GetProductGuid()) +
                                "\n Характеристика: " + appState.characteristicStructureModel.FindCharacteristicByGuid(nomenclature.GetCharacteristicGUID()) +
                                "\n Вес: " + nomenclature.GetWeight().toString() + "\n\n");
            }
        }
        catch (ApplicationException e)
        {
            ((MainActivity) Activity).AlarmAndNotify(e.getMessage());
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

                                    ProductStructureModel orderProduct =  CheckInOrder(result[0]);
                                    appState.SelectedDialogNomenclatures.put(barcode.getUniqueIdentifier(), orderProduct);
                                    SuccessSaveData(((MainActivity) Activity).IsBarcodeInfoFragmentShowed, orderProduct, barcode);


                                } catch (DoesNotExistsInOrderException ex) {
                                    ((MainActivity) Activity).AlarmAndNotify(ex.getMessage());
                                }
                                finally {

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

    @Override
    public void ParseData(ScanDataCollection.ScanData data) {
        try {
            ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(data.getData(), BarcodeTypes.GetType(data.getLabelType()));
            ProductStructureModel product = ParseAction(barcode);

            if(product!=null)
            {
                SuccessSaveData(((MainActivity) Activity).IsBarcodeInfoFragmentShowed, product, barcode);
            }

        } catch (ApplicationException ex) {
            ((MainActivity) Activity).AlarmAndNotify(ex.getMessage());
        } catch (ParseException e) {
            ((MainActivity) Activity).AlarmAndNotify(e.getMessage());
        } catch (DoesNotExistsInOrderException e) {
            ((MainActivity) Activity).AlarmAndNotify(e.getMessage());
        }
    }

    public ProductStructureModel ParseAction(ScanningBarcodeStructureModel barcode) throws ApplicationException, ParseException, DoesNotExistsInOrderException {
            this.barcodeScanningLogic.IsBarcodeTypeAllowedToScan(barcode.getLabelType());
            this.barcodeScanningLogic.IsBarcodeAllowedToScan(ScannerState.PRODUCT); // We are in Product command, so State = Product scanning

            ArrayList<ProductStructureModel> products =
                    this.BarcodeProductLogic.FindProductByBarcode(barcode.getUniqueIdentifier());

            if (products.size() > 1)
            {
                if(appState.SelectedDialogNomenclatures.containsKey(barcode.getUniqueIdentifier()))
                {
                    ProductStructureModel orderProduct =  CheckInOrder(appState.SelectedDialogNomenclatures.get(barcode.getUniqueIdentifier()));
                    return orderProduct;
                }
                else
                    {
                    SelectionDialog(products, barcode);
                }
            }
            else
            {
                ProductStructureModel orderProduct =  CheckInOrder(products.get(0));
                return orderProduct;
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

    @Override
    protected void SaveInfoForProductList(ScanningBarcodeStructureModel barcode, ObjectForSaving product)
    {
        try {

            FullDataTableControl.Details detailsModel = this.responseModelGenerator.CreateFullDataTableResponse(barcode,(ProductStructureModel) product);
            //appState.ScannedProductsToSend.Add(detailsModel);
            ((MainActivity) this.Activity).new AsyncListViewDataUpdate(detailsModel).execute();
        }
        catch (ApplicationException e)
        {
            ((MainActivity) Activity).AlarmAndNotify(e.getMessage());
        }
    }

    @Override
    protected void ShowInfoForFragment(ScanningBarcodeStructureModel barcode, ObjectForSaving product)
    {
        String result = this.responseModelGenerator.CreateStringResponse(barcode,(ProductStructureModel) product);
        ((MainActivity) this.Activity).new AsyncBarcodeInfoUpdate().execute(result);
    }
}

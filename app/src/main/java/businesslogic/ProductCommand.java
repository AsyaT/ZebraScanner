package businesslogic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.ScannerException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScannerApplication;

public class ProductCommand implements Command {

    ListViewPresentationModel viewUpdateModel = null;
    ProductHelper productHelper;
    MediaPlayer mediaPlayer;
    BarcodeStructure barCode;
    ProductModel.ProductListModel productListModel;
    Activity Activity;
    Scanner CurrentScanner;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;
        this.CurrentScanner = ((MainActivity)activity).getScanner();

        ScannerApplication appState = ((ScannerApplication) Activity.getApplication());
        productHelper = appState.productHelper;

        mediaPlayer = MediaPlayer.create(activity, R.raw.beep01);

    }

    protected void SelectionDialog(List<ProductModel.PropertiesListModel> listNomenclature) {


        List<CharSequence> nomenclatures = new ArrayList<CharSequence>();
        final ProductModel.PropertiesListModel[] result = {null};

        for(ProductModel.PropertiesListModel nomenclature : listNomenclature)
        {
            nomenclatures.add(nomenclature.ProductName+"\n Характеристика: "+nomenclature.ProductCharactName+"\n Вес: "+nomenclature.Quant+"\n\n");
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
                                    if (productListModel != null) {

                                        viewUpdateModel = new ListViewPresentationModel(
                                                barCode.getUniqueIdentifier(),
                                                result[0].ProductName,
                                                result[0].ProductCharactName,
                                                WeightCalculation(result[0]),
                                                result[0].ProductGUID);

                                        PostAction();

                                        try {
                                            CurrentScanner.enable();
                                            CurrentScanner.read();
                                        } catch (ScannerException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (Exception ex)
                                {
                                    ex.getMessage();
                                }
                                dialogInterface.dismiss();
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


    @Override
    public void ParseData(ScanDataCollection.ScanData data) {

        if(((MainActivity)this.Activity).IsDeniedToScan(data.getLabelType()) == true)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.Activity);

            this.Activity.runOnUiThread(new Runnable() {
                public void run() {
                    builder.setTitle("Такой тип запрещен к сканированию")
                    .setMessage("Сканируйте другой штрих-код")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                CurrentScanner.enable();
                                CurrentScanner.read();
                            } catch (ScannerException e) {
                                e.printStackTrace();
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
        else{

            try {
                barCode = new BarcodeStructure(data.getData(), BarcodeTypes.GetType(data.getLabelType()));

                productListModel =  productHelper.FindProductByBarcode(barCode.getUniqueIdentifier());

                if(productListModel == null)
                {
                    return;
                }

                ProductModel.PropertiesListModel propertiesListModel = null;

                if(productListModel.PropertiesList.size()>1)
                {
                    SelectionDialog((List<ProductModel.PropertiesListModel>)productListModel.PropertiesList);
                }
                else
                {
                    propertiesListModel = productListModel.PropertiesList.get(0);

                    viewUpdateModel = new ListViewPresentationModel(
                            barCode.getUniqueIdentifier(),
                            propertiesListModel.ProductName,
                            propertiesListModel.ProductCharactName,
                            WeightCalculation(propertiesListModel),
                            propertiesListModel.ProductGUID);

                }
            }
            catch (Exception ex)
            {
                ex.getMessage();
            }
        }
    }

    private Double WeightCalculation(ProductModel.PropertiesListModel propertiesListModel ) throws ParseException {

        if(barCode.getWeight() == null)
        {
            return propertiesListModel.Quantity();
        }
        else {
            return barCode.getWeight();
        }
    }

    @Override
    public void PostAction() {

        if(viewUpdateModel == null && productListModel!=null)
        {
            return;
        }

        if (productListModel == null)
        {
            try {
                CurrentScanner.disable();
            } catch (ScannerException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();

            if (((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed)
            {
                ((MainActivity)this.Activity).new AsyncBarcodeInfoUpdate().execute("Такой штрихкод не найден в коллекции");
            }
            else {
                ((MainActivity)this.Activity).new MessageDialog().execute("Такой штрихкод не найден в коллекции");
            }

        }
        else if(((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed == false)
        {
            if (barCode.getLabelType() == BarcodeTypes.LocalEAN13 ) {

                ((MainActivity)this.Activity).new BaseAsyncDataUpdate(viewUpdateModel).execute();
            }

            else if(barCode.getLabelType() == BarcodeTypes.LocalGS1_EXP){
/*
                SQLiteDBHelper dbHandler = new SQLiteDBHelper(this);
                dbHandler.insertDatabar(
                        barCode.getUniqueIdentifier(),
                        barCode.getWeight().toString(),
                        barCode.getLotNumber(),
                        barCode.getProductionDate(),
                        barCode.getExpirationDate(),
                        barCode.getSerialNumber(),
                        barCode.getInternalProducer(),
                        barCode.getInternalEquipment() );

 */
                ((MainActivity)this.Activity).new BaseAsyncDataUpdate(viewUpdateModel).execute();
            }
        }
        else if (((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed == true)
        {
            String resultText="";

            if (barCode.getLabelType() == BarcodeTypes.LocalEAN13 && barCode.getWeight() != null) {
                resultText="Штрих-код: "+barCode.getUniqueIdentifier()+"\nНоменклатура: "+ viewUpdateModel.Nomenclature +"\nВес: "+barCode.getWeight();
            }
            else if(barCode.getLabelType() == BarcodeTypes.LocalEAN13 && barCode.getWeight()== null){
                resultText="Штрих-код: "+barCode.getUniqueIdentifier()+"\nНоменклатура: "+ viewUpdateModel.Nomenclature;
            }
            else if(barCode.getLabelType() == BarcodeTypes.LocalGS1_EXP){
                resultText=
                        "Штрих-код: "+barCode.getUniqueIdentifier()
                                + "\nНоменклатура: "+ viewUpdateModel.Nomenclature
                                + "\nВес: "+barCode.getWeight()+" кг"
                                + "\nНомер партии: "+barCode.getLotNumber()
                                + "\nДата производства: "+ new SimpleDateFormat("dd-MM-yyyy").format(barCode.getProductionDate())
                                + "\nДата истечения срока годност: " + new SimpleDateFormat("dd-MM-yyyy").format(barCode.getExpirationDate())
                                + "\nСерийный номер: " + barCode.getSerialNumber()
                                + "\nВнутренний код производителя: " + barCode.getInternalProducer()
                                + "\nВнутренний код оборудования: " + barCode.getInternalEquipment();
            }

            ((MainActivity)this.Activity).new AsyncBarcodeInfoUpdate().execute(resultText);

        }

    }
}

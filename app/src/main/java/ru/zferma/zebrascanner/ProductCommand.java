package ru.zferma.zebrascanner;

import android.app.Activity;
import android.media.MediaPlayer;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.ScannerException;

import java.text.SimpleDateFormat;

public class ProductCommand implements Command   {

    ListViewPresentationModel viewUpdateModel;
    ProductHelper productHelper;
    MediaPlayer mediaPlayer;
    BarcodeStructure barCode;
    ProductModel.ProductListModel productListModel;
    Activity Activity;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;
        productHelper = ((MainActivity)activity).productHelper;

        mediaPlayer = MediaPlayer.create(activity, R.raw.beep01);

    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data) {
        try {
            barCode = new BarcodeStructure(data.getData(), BarcodeTypes.GetType(data.getLabelType()));

            productListModel =  productHelper.FindProductByBarcode(barCode.getUniqueIdentifier());

            //TODO: Dialog to chose nomenclature

            if(productListModel !=null)
            {
                double weight;
                if(barCode.getWeight() == null)
                {
                    weight = productListModel.PropertiesList.get(0).Quantity();
                }
                else {
                    weight = barCode.getWeight();
                }

                viewUpdateModel = new ListViewPresentationModel(barCode.getUniqueIdentifier(), productListModel.PropertiesList.get(0).ProductName, weight);
            }
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }
    }

    @Override
    public void PostAction(Scanner scanner) {
        if (productListModel == null)
        {
            try {
                scanner.disable();
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

                ((MainActivity)this.Activity).new BaseAsyncDataUpdate().execute(viewUpdateModel);
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
                ((MainActivity)this.Activity).new BaseAsyncDataUpdate().execute(viewUpdateModel);
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

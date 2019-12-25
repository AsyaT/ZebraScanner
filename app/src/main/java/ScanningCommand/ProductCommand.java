package ScanningCommand;

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

import businesslogic.CharacterisiticStructureModel;
import businesslogic.ProductStructureModel;
import businesslogic.ScanningBarcodeStructureModel;
import businesslogic.BarcodeTypes;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScannerApplication;
import businesslogic.BarcodeStructureModel;

public class ProductCommand implements Command {

    ListViewPresentationModel viewUpdateModel = null;
    BarcodeStructureModel BarcodeStructureModel;
    ProductStructureModel ProductStructureModel;
    CharacterisiticStructureModel CharacterisiticStructureModel;

    MediaPlayer mediaPlayer;
    ScanningBarcodeStructureModel barCode;
    List<businesslogic.BarcodeStructureModel.ProductStructureModel> ProductModel;
    Activity Activity;
    Scanner CurrentScanner;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;
        this.CurrentScanner = ((MainActivity)activity).getScanner();

        ScannerApplication appState = ((ScannerApplication) Activity.getApplication());
        BarcodeStructureModel = appState.barcodeStructureModel;
        ProductStructureModel = appState.productStructureModel;
        CharacterisiticStructureModel = appState.characterisiticStructureModel;

        mediaPlayer = MediaPlayer.create(activity, R.raw.beep01);

    }

    protected void SelectionDialog(List<BarcodeStructureModel.ProductStructureModel> listNomenclature) {


        List<CharSequence> nomenclatures = new ArrayList<CharSequence>();
        final BarcodeStructureModel.ProductStructureModel[] result = {null};

        for(BarcodeStructureModel.ProductStructureModel nomenclature : listNomenclature)
        {
            nomenclatures.add(
                    this.ProductStructureModel.FindProductByGuid( nomenclature.GetProductGuid())+
                            "\n Характеристика: "+this.CharacterisiticStructureModel.FindCharacteristicByGuid(nomenclature.GetCharacteristicGUID())+
                            "\n Вес: "+nomenclature.GetQuantity().toString()+"\n\n");
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
                                    if (ProductModel != null) {

                                        viewUpdateModel = new ListViewPresentationModel(
                                                barCode.getUniqueIdentifier(),
                                                ProductStructureModel.FindProductByGuid( result[0].GetProductGuid()),
                                                CharacterisiticStructureModel.FindCharacteristicByGuid(result[0].GetCharacteristicGUID()),
                                                WeightCalculation(result[0].GetQuantity()),
                                                result[0].GetProductGuid());

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

        if(((MainActivity)this.Activity).IsAllowedToScan(data.getLabelType()) == false)
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
                barCode = new ScanningBarcodeStructureModel(data.getData(), BarcodeTypes.GetType(data.getLabelType()));
                ProductModel = BarcodeStructureModel.FindProductByBarcode(barCode.getUniqueIdentifier());

                if(ProductModel == null)
                {
                    return;
                }

                BarcodeStructureModel.ProductStructureModel propertyModel = null;

                if(ProductModel.size()>1)
                {
                    SelectionDialog(ProductModel);
                }
                else
                {
                    propertyModel = ProductModel.get(0);

                    viewUpdateModel = new ListViewPresentationModel(
                            barCode.getUniqueIdentifier(),
                            this.ProductStructureModel.FindProductByGuid(propertyModel.GetProductGuid()),
                            this.CharacterisiticStructureModel.FindCharacteristicByGuid(propertyModel.GetCharacteristicGUID()),
                            WeightCalculation(propertyModel.GetQuantity()),
                            propertyModel.GetProductGuid());

                }
            }
            catch (Exception ex)
            {
                ex.getMessage();
            }
        }
    }

    private Double WeightCalculation(Double modelQuantity ) throws ParseException {

        if(barCode.getWeight() == null)
        {
            return modelQuantity;
        }
        else {
            return barCode.getWeight();
        }
    }

    @Override
    public void PostAction() {

        if(viewUpdateModel == null && ProductModel!=null)
        {
            return;
        }

        if (ProductModel == null)
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

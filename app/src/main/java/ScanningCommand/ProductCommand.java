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

import businesslogic.BarcodeStructureModel;
import businesslogic.BarcodeTypes;
import businesslogic.CharacterisiticStructureModel;
import businesslogic.FullDataTableControl;
import businesslogic.OrderStructureModel;
import businesslogic.NomenclatureStructureModel;
import businesslogic.ScanningBarcodeStructureModel;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScannerApplication;

public class ProductCommand implements Command {

    ListViewPresentationModel viewUpdateModel = null;

    BarcodeStructureModel BarcodeStructureModel = null;
    NomenclatureStructureModel NomenclatureStructureModel = null;
    CharacterisiticStructureModel CharacterisiticStructureModel = null;
    OrderStructureModel OrderStructureModel = null;

    MediaPlayer mediaPlayer;
    ScanningBarcodeStructureModel barCode;

    Activity Activity;
    Scanner CurrentScanner;
    ScannerApplication appState;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;
        this.CurrentScanner = ((MainActivity)activity).getScanner();

        appState = ((ScannerApplication) Activity.getApplication());
        BarcodeStructureModel = appState.barcodeStructureModel;
        NomenclatureStructureModel = appState.nomenclatureStructureModel;
        CharacterisiticStructureModel = appState.characterisiticStructureModel;
        OrderStructureModel = appState.orderStructureModel;

        mediaPlayer = MediaPlayer.create(activity, R.raw.beep01);

    }

    protected void SelectionDialog(List<BarcodeStructureModel.ProductStructureModel> listNomenclature) {


        List<CharSequence> nomenclatures = new ArrayList<CharSequence>();
        final BarcodeStructureModel.ProductStructureModel[] result = {null};

        for(BarcodeStructureModel.ProductStructureModel nomenclature : listNomenclature)
        {
            nomenclatures.add(
                    this.NomenclatureStructureModel.FindProductByGuid( nomenclature.GetProductGuid())+
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

                                if((OrderStructureModel != null) && (OrderStructureModel.IfProductExists(result[0].GetProductGuid()) == false))
                                {
                                    AlarmAndNotify("Такой продукт не найден в заказе");

                                }
                                else
                                {
                                    CreateResultModels(barCode.getUniqueIdentifier(), result[0]);

                                    PostAction();

                                    try {
                                        CurrentScanner.enable();
                                        CurrentScanner.read();
                                    } catch (ScannerException e) {
                                        e.printStackTrace();
                                    }
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

    protected void AlarmAndNotify(String message)
    {
        try {
            CurrentScanner.disable();
        } catch (ScannerException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();

        if (((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed)
        {
            ((MainActivity)this.Activity).new AsyncBarcodeInfoUpdate().execute(message);
        }
        else {
            ((MainActivity)this.Activity).new MessageDialog().execute(message);
        }
    }

    protected String ParseBarcode(String barcode, BarcodeTypes type) throws ParseException {
        barCode = new ScanningBarcodeStructureModel(barcode, type);
        return barCode.getUniqueIdentifier();
    }

    protected businesslogic.BarcodeStructureModel.ProductStructureModel GetProductFromCollection(String uniqueIdentifierBarcode)
    {
        List<businesslogic.BarcodeStructureModel.ProductStructureModel> listOfProducts = BarcodeStructureModel.FindProductByBarcode(uniqueIdentifierBarcode);

        if(listOfProducts == null)
        {
            AlarmAndNotify("Такой штрих-код не найден в номенклатуре!");
        }
        else
        {
            businesslogic.BarcodeStructureModel.ProductStructureModel product=null;
            if(listOfProducts.size()>1)
            {
                SelectionDialog(listOfProducts);
            }
            else
            {
                product = listOfProducts.get(0);
            }

            if((this.OrderStructureModel != null) && (this.OrderStructureModel.IfProductExists(product.GetProductGuid()) == false))
            {
                AlarmAndNotify("Такой продукт не найден в заказе");

            }
            else
            {
                return product;
            }
        }
        return null;
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data) {

        if(appState.LocationContext.IsAllowed(data.getLabelType()) == false)
        {
            AlarmAndNotify("Тип "+data.getLabelType().name() +" запрещен к сканированию.");
        }
        else
        {
            try {
                String uniqueIdentifier = ParseBarcode(data.getData(), BarcodeTypes.GetType(data.getLabelType()));

                businesslogic.BarcodeStructureModel.ProductStructureModel product = GetProductFromCollection(uniqueIdentifier);

                if(product!=null) {

                    CreateResultModels(uniqueIdentifier, product);
                }

            }
            catch (Exception ex)
            {
                ex.getMessage();
            }
        }
    }

    private void CreateResultModels(String uniqueIdentifier, businesslogic.BarcodeStructureModel.ProductStructureModel product)
    {
        viewUpdateModel = new ListViewPresentationModel(
                uniqueIdentifier,
                this.NomenclatureStructureModel.FindProductByGuid(product.GetProductGuid()),
                this.CharacterisiticStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGUID()),
                WeightCalculation(product.GetQuantity()),
                product.GetProductGuid());

        if(appState.ScannedProductsToSend == null)
        {
            appState.ScannedProductsToSend = new FullDataTableControl();
        }
        appState.ScannedProductsToSend.Add(
                product.GetProductGuid(),
                product.GetCharacteristicGUID(),
                appState.manufacturerStructureModel.GetManufacturerGuid(barCode.getInternalProducer()),
                barCode);
    }

    private Double WeightCalculation(Double modelQuantity )  {

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

        if(viewUpdateModel == null )
        {
            return;
        }

        if(((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed == false)
        {
            ((MainActivity)this.Activity).new BaseAsyncDataUpdate( viewUpdateModel).execute();

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

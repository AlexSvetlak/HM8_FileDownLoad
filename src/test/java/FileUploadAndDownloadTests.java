import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.xlstest.XLS;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.codeborne.selenide.Selenide.open;

public class FileUploadAndDownloadTests {

    private String URL = "https://demoqa.com/upload-download";
    private String FILENAME = "t8i0r1.jpg";
    private SelenideElement uploadFile = $("#uploadFile");
    private SelenideElement uploadedFilePath = $("#uploadedFilePath");

    @Test
    @DisplayName("Загрузка файла по относительному пути")
    public void uploadPicturesAndCheckPicturesName() {
        open(URL); // Открыть Браузер
        uploadFile.uploadFromClasspath(FILENAME); // Загрузка из resources
        uploadedFilePath.shouldHave(Condition.text(FILENAME)); // Проверяем наличие загруженного рисунка
    }

    private SelenideElement exemlpeRew = $("#raw-url");
    private String downloadURL = "https://github.com/AlexSvetlak/HM_5_Pageobject/blob/master/Readme.txt";

    @Test
    @DisplayName("Скачать файл")
    public void downloadFileAndChekText() throws IOException {
        open(downloadURL);
        File txt = exemlpeRew.download();
        String fileContent = IOUtils.toString(new FileReader(txt));
        assertTrue(fileContent.contains("Текст для теста по загрузке и проверке содержимого файла"));

    }

    private String dowloadPDFURL = "https://www.almacom.kz/";
    private SelenideElement pdfFile = $(byText("Прайс"));

    @Test
    @DisplayName("Скачать pdf")
    public void dowloadPdfAndCheck() throws IOException {
        open(dowloadPDFURL);
        File pdf = pdfFile.download();
        PDF newPDF = new PDF(pdf);
        Assertions.assertEquals(14, newPDF.numberOfPages);
    }

    private String xlsUrl = "https://security.kz/";
    private SelenideElement downloadXLS = $(byText("Прайс лист на монтаж и сервисное обслуживание"));



    @Test
    @DisplayName("Загрузка xls")

    public void downloadXLS() throws FileNotFoundException {
        open(xlsUrl);
        File xls = downloadXLS.download();
        XLS parsXls = new XLS(xls);

        boolean chekXls = parsXls.excel
                .getSheetAt(1)
                .getRow(9102)
                .getCell(0)
                .getStringCellValue()
                .contains("Прайс-лист (Розничный)");

        assertTrue(chekXls);

    }
}

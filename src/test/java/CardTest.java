import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardTest {

    @BeforeAll
    static void serUpAll (){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll (){
        SelenideLogger.removeListener("allure");
    }

    private String Date (int addDays, String patten) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(patten));
    }

    @Test
    void CardDeliveryTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        String currentDate = Date(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $x("//input [@name = 'name']").setValue("Коновалова Мария");
        $x("//input [@name ='phone']").setValue("+79996665544");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactOwnText("Встреча успешно забронирована на " + currentDate));
    }
}

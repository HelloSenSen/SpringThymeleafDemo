package springdemo.date;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 字符串与引用类型LocalDate 的转换器
 * @author Sunnysen
 */
public class USLocalDateFormatter implements Formatter<LocalDate> {

    public static final String US_PATTERN = "MM/dd/yyyy";
    public static final String NORMAL_PATTERN = "yyyy-MM-dd";

    /**
     * 重写解析方法
     * 将文本类型转为Locale类型
     * @param text
     * @param locale
     * @return
     * @throws ParseException
     */
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text,DateTimeFormatter.ofPattern(getPattern(locale)));
    }

    /**
     * 重写打印方法
     * 将Locale类型转为文本类型
     * @param object
     * @param locale
     * @return
     */
    @Override
    public String print(LocalDate object, Locale locale) {
        return DateTimeFormatter.ofPattern(getPattern(locale)).format(object);
    }

    public static String getPattern(Locale locale){
        return isUnitedStates(locale)?US_PATTERN:NORMAL_PATTERN;
    }

    public static boolean isUnitedStates(Locale locale){
        return Locale.US.getCountry().equals(locale.getCountry());
    }

}

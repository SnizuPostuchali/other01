package streams.optional;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class OptionalTest {
    public static void main(String[] args)
            throws Exception
    {
        var contents = new String(Files.readAllBytes(Paths.get("C:\\Users\\Master\\Desktop\\X\\streamEx.txt")), StandardCharsets.UTF_8);
        List<String> wordList = List.of(contents.split("\\PL+"));

        Optional<String> optionalValue = wordList.stream().filter(s -> s.contains("di")).findFirst();
        System.out.println(optionalValue.orElse("No word") + " contains di");

        Optional<String> optionalString = Optional.empty();
        String result = optionalString.orElse("N/A");
        System.out.println("result: " + result);
        result = optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
        System.out.println("result: " + result);
        try{
            result = optionalString.orElseThrow(IllegalStateException::new);
            System.out.println("result: " + result);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        TimeUnit.SECONDS.sleep(1);

        optionalValue = wordList.stream().filter(s -> s.contains("ed")).findFirst();
        optionalValue.ifPresent(s -> System.out.println(s + " contains ed"));

        var results = new HashSet<String>();
        optionalValue.ifPresent(results::add);
        System.out.println(optionalValue);
        System.out.println(results);
        Optional<Boolean> added = optionalValue.map(results::add);
        System.out.println(added);

        System.out.println(inverse(4.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(-1.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(0.0).flatMap(OptionalTest::squareRoot));
        Optional<Double> result2 = Optional.of(-4.0).flatMap(OptionalTest::inverse).flatMap(OptionalTest::squareRoot);
        System.out.println(result2);
    }

    public static Optional<Double> inverse(Double x ){
        return x==0 ? Optional.empty() : Optional.of(1/x);
    }

    public static Optional<Double> squareRoot(Double x){
        return x<0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}

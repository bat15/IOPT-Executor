package bat15;

import org.junit.Test;

import javax.json.Json;

/**
 * Created by xoton on 17.03.2017.
 */
public class JSONBuilderTest {


    @Test
    public void test1() {
        String kek = Json.createObjectBuilder()
                .add("test", "test").build().toString();

        System.out.println(kek);
    }
}
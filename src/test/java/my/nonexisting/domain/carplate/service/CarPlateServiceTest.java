package my.nonexisting.domain.carplate.service;

import java.util.regex.Pattern;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Texhnolyze
 */
public class CarPlateServiceTest {
    
    public CarPlateServiceTest() {
    }
    
    /**
     * Test of randomCarPlate method, of class CarPlateService.
     */
    @Test
    public void testRandomCarPlate() {
        System.out.println("randomCarPlate");
        Pattern p = Pattern.compile("[АЕТОРНУКХСВМ]{1}[0-9]{3}[АЕТОРНУКХСВМ]{2} 116 RUS");
        CarPlateService service = new CarPlateService();
        for (int i = 0; i < 1000; i++) {
            assertTrue(p.matcher(service.randomCarPlate()).matches());
        }
    }

    /**
     * Test of nextCarPlate method, of class CarPlateService.
     */
    @Test
    public void testNextCarPlate() {
        System.out.println("nextCarPlate");
        CarPlateService service = new CarPlateService();
        assertEquals("C400BA 116 RUS", service.nextCarPlate("C399BA 116 RUS"));
        assertEquals("С000ВВ 116 RUS", service.nextCarPlate("С999ВА 116 RUS"));
        assertEquals("А000АА 116 RUS", service.nextCarPlate("Х999ХХ 116 RUS"));
    }
    
}

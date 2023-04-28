package shopAndGoods;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

public class ShopTest {
    // TODO
    private Shop shop;

    @Before
    public void setUp() {
        this.shop = new Shop();
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testGetShelvesReturnsUnmodifiableCollectionException() {
        shop.getShelves().clear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddGoodsToShelfThrowsExceptionForNotExisting() throws OperationNotSupportedException {
        shop.addGoods("Nothing", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddGoodsToShelfThrowsExceptionsForTakenShelve() throws OperationNotSupportedException {
        Goods good = new Goods("Test", "1234");
        shop.addGoods("Shelves1", good);
        shop.addGoods("Shelves1", good);

    }

    @Test(expected = OperationNotSupportedException.class)
    public void testAddGoodsGoodAlreadyExistsOnShelveException() throws OperationNotSupportedException {
        Goods good = new Goods("Test", "1234");
        shop.addGoods("Shelves1", good);
        shop.addGoods("Shelves2", good);
    }

    @Test
    public void testAddGoodsAddsCorrectlyAndReturnsCorrectMessage() throws OperationNotSupportedException {
        Goods goods = new Goods("Test3", "12345");
        String actual = shop.addGoods("Shelves1", goods);
        String expected = "Goods: 12345 is placed successfully!";
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveGoodsThrowsExceptionForNonExistingShelve() {
        shop.removeGoods("Nothing", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveGoodsThrowsExceptionForNonExistingGoodInShelve() {
        shop.removeGoods("Shelves1", new Goods("23412", "4231"));
    }

    @Test
    public void testRemoveGoodsSuccessFully() throws OperationNotSupportedException {
        Goods goods = new Goods("TestGood", "test_code_good");
        shop.addGoods("Shelves1", goods);
        String actual = shop.removeGoods("Shelves1", goods);
        String expected = "Goods: test_code_good is removed successfully!";
        assertEquals(expected, actual);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveGoodsShouldFailForDifferentGoodOnShelve() throws OperationNotSupportedException {
        Goods goods1 = new Goods("TestGood", "test_code_good");
        Goods goods2 = new Goods("TestGoodV2", "test_code_goodV2");
        shop.addGoods("Shelves1", goods1);
        shop.removeGoods("Shelves1", goods2);
    }

    @Test
    public void testRemoveGoodsShouldSetShelveValueToNull() throws OperationNotSupportedException {
        Goods good = new Goods("TestGood", "test_code_good");
        shop.addGoods("Shelves1", good);
        shop.removeGoods("Shelves1",good);
        Goods empty = shop.getShelves().get("Shelves1");
        assertNull(empty);
    }
}
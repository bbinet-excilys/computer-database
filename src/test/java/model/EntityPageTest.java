package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class EntityPageTest {

  private final Integer     VALID_PAGE_SIZE = 1;
  private final Integer     VALID_OFFSET    = 0;
  final static List<Entity> VALID_ENTITIES  = new ArrayList<>();
  final static List<Entity> EMPTY_ENTITIES  = new ArrayList<>();
  final static Entity       ENTITY          = Mockito.mock(Entity.class);

  private final Integer INVALID_PAGE_SIZE = -1;

  @BeforeClass
  public static void init() {
    VALID_ENTITIES.add(ENTITY);
  }

  @Test
  public void EntityPage_Valid() {
    EntityPage ePage = new EntityPage<Entity>(this.VALID_PAGE_SIZE, this.VALID_OFFSET, EntityPageTest.VALID_ENTITIES);
    assertEquals(EntityPageTest.VALID_ENTITIES, ePage.getEntities());
    assertEquals(this.VALID_PAGE_SIZE, ePage.getPageSize());
    assertEquals(this.VALID_OFFSET, ePage.getOffset());
  }

  @Test
  public void EntityPage_GetPagePrevious() {
    EntityPage ePage = new EntityPage<Entity>(this.VALID_PAGE_SIZE, this.VALID_OFFSET, EntityPageTest.VALID_ENTITIES);
    assertEquals(EntityPageTest.VALID_ENTITIES, ePage.getPage(-1));
  }

  @Test
  public void EntityPage_GetPageNext() {
    EntityPage ePage = new EntityPage<Entity>(this.VALID_PAGE_SIZE, this.VALID_OFFSET, EntityPageTest.VALID_ENTITIES);
    assertEquals(EntityPageTest.VALID_ENTITIES, ePage.getPage(1));
  }

  @Test
  public void EntityPage_GetPageCurrent() {
    EntityPage ePage = new EntityPage<Entity>(this.VALID_PAGE_SIZE, this.VALID_OFFSET, EntityPageTest.VALID_ENTITIES);
    assertEquals(EntityPageTest.VALID_ENTITIES, ePage.getPage(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void EntityPage_NullEntities() {
    EntityPage ePage = new EntityPage<Entity>(this.VALID_PAGE_SIZE, this.VALID_OFFSET, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void EntityPage_EmptyEntities() {
    EntityPage ePage = new EntityPage<Entity>(this.VALID_PAGE_SIZE, this.VALID_OFFSET, EntityPageTest.EMPTY_ENTITIES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void EntityPage_PageSizeNull() {
    EntityPage ePage = new EntityPage<Entity>(null, this.VALID_OFFSET, EntityPageTest.VALID_ENTITIES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void EntityPage_OffsetNull() {
    EntityPage ePage = new EntityPage<Entity>(this.VALID_PAGE_SIZE, null, EntityPageTest.VALID_ENTITIES);
  }

  @Test(expected = IllegalArgumentException.class)
  public void EntityPage_PageSizeNegative() {
    EntityPage ePage = new EntityPage<Entity>(this.INVALID_PAGE_SIZE, this.VALID_OFFSET, EntityPageTest.VALID_ENTITIES);
  }
}

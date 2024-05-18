package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.repository.ItemRepository;

/**
 * study.datajpa.entity ItemRepositoryTest
 *
 * @author : K
 */
@SpringBootTest
class ItemRepositoryTest {

  @Autowired
  ItemRepository itemRepository;

  @Test
  public void save() {
    Item item = new Item("A");
    itemRepository.save(item);
  }
  
  
}

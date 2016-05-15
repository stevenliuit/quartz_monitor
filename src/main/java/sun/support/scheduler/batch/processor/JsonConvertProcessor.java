package sun.support.scheduler.batch.processor;

import org.springframework.batch.item.ItemProcessor;

/**
 * Created by root on 2016/3/3.
 */
public class JsonConvertProcessor implements ItemProcessor<Object, Tuple<String, Object>> {

    private ItemConvert<String, Object> jsonConvert;

    private ItemAdapter itemAdapter;

    public void setItemAdapter(ItemAdapter itemAdapter) {
        this.itemAdapter = itemAdapter;
    }

    public void setJsonConvert(ItemConvert<String, Object> jsonConvert) {
        this.jsonConvert = jsonConvert;
    }

    @Override
    public Tuple<String, Object> process(Object item) throws Exception {
        assert jsonConvert != null;

        if(itemAdapter != null){
            item = itemAdapter.adapter(item);
        }
        String json = jsonConvert.convert(item);
        return new Tuple<>(json, item);
    }


}

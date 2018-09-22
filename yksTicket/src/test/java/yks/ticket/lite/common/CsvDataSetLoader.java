package yks.ticket.lite.common;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

/**
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public class CsvDataSetLoader extends AbstractDataSetLoader {

	/**
	 * 
	 * @since 0.0.1
	 */
	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		return new CsvURLDataSet(resource.getURL());
	}
}
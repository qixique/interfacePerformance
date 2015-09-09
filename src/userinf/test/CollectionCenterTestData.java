package userinf.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

public class CollectionCenterTestData {
	@DataProvider(name = "data_DealAndMallSaledProductId")
	public static Object[][] dataDealAndMallSaledProductId(){
		/**
		 * 222422024 : 有一个全站deal，类型为product,普通售卖，deal不可售，但是商城cd/bj可售
		 */
		Map<String,String> dealInfoUnsell_222422024 = new HashMap<String,String>();
		dealInfoUnsell_222422024.put("product_id", "222422024");
		dealInfoUnsell_222422024.put("hash_id", "md222422024hd");
		dealInfoUnsell_222422024.put("is_on_sale", "0");
		
		Map<String,String> mallInfoUnsell_222422024 = new HashMap<String,String>();
		mallInfoUnsell_222422024.put("mall_id", "222422024");
		mallInfoUnsell_222422024.put("mall_product_name", "mall_product_name_222422024");
		mallInfoUnsell_222422024.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422024 = new HashMap<String,String>();
		mallInfoSell_222422024.put("mall_id", "222422024");
		mallInfoSell_222422024.put("mall_product_name", "mall_product_name_222422024");
		mallInfoSell_222422024.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422024 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422024.put("product", mallInfoSell_222422024);
		categoryInfo_all_222422024.put("pop_cosmetics", mallInfoSell_222422024);
		categoryInfo_all_222422024.put("media", mallInfoSell_222422024);
		categoryInfo_all_222422024.put("global", mallInfoSell_222422024);
		categoryInfo_all_222422024.put("retail_global", mallInfoSell_222422024);
		categoryInfo_all_222422024.put("combination_global", mallInfoSell_222422024);
		Map<String,Map<String,String>> categoryInfo_gz_222422024 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422024.put("product", dealInfoUnsell_222422024);
		categoryInfo_gz_222422024.put("pop_cosmetics", mallInfoUnsell_222422024);
		categoryInfo_gz_222422024.put("media", mallInfoUnsell_222422024);
		categoryInfo_gz_222422024.put("global", mallInfoUnsell_222422024);
		categoryInfo_gz_222422024.put("retail_global", mallInfoUnsell_222422024);
		categoryInfo_gz_222422024.put("combination_global", mallInfoUnsell_222422024);
		
		Map<String,Map<String,Map<String,String>>> siteData_222422024 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422024.put("", categoryInfo_all_222422024);
		siteData_222422024.put("cd", categoryInfo_all_222422024);
		siteData_222422024.put("bj", categoryInfo_all_222422024);
		siteData_222422024.put("sh", categoryInfo_gz_222422024);
		siteData_222422024.put("gz", categoryInfo_gz_222422024);
		/**
		 * 222422025 : 有一个全站deal，类型为product,普通售卖，deal可售,库存分站，但是商城cd/bj可售
		 */
		Map<String,String> dealInfoSell_222422025 = new HashMap<String,String>();
		dealInfoSell_222422025.put("product_id", "222422025");
		dealInfoSell_222422025.put("hash_id", "md222422025hd");
		dealInfoSell_222422025.put("is_on_sale", "1");
		
		Map<String,String> dealInfoUnsell_222422025 = new HashMap<String,String>();
		dealInfoUnsell_222422025.put("product_id", "222422025");
		dealInfoUnsell_222422025.put("hash_id", "md222422025hd");
		dealInfoUnsell_222422025.put("is_on_sale", "0");
		
		Map<String,String> mallInfoUnsell_222422025 = new HashMap<String,String>();
		mallInfoUnsell_222422025.put("mall_id", "222422025");
		mallInfoUnsell_222422025.put("mall_product_name", "mall_product_name_222422025");
		mallInfoUnsell_222422025.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422025 = new HashMap<String,String>();
		mallInfoSell_222422025.put("mall_id", "222422025");
		mallInfoSell_222422025.put("mall_product_name", "mall_product_name_222422025");
		mallInfoSell_222422025.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422025 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422025.put("product", dealInfoSell_222422025);
		categoryInfo_all_222422025.put("pop_cosmetics", mallInfoSell_222422025);
		categoryInfo_all_222422025.put("media", mallInfoSell_222422025);
		categoryInfo_all_222422025.put("global", mallInfoSell_222422025);
		categoryInfo_all_222422025.put("retail_global", mallInfoSell_222422025);
		categoryInfo_all_222422025.put("combination_global", mallInfoSell_222422025);		
		Map<String,Map<String,String>> categoryInfo_gz_222422025 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422025.put("product", dealInfoUnsell_222422025);
		categoryInfo_gz_222422025.put("pop_cosmetics", mallInfoUnsell_222422025);
		categoryInfo_gz_222422025.put("media", mallInfoUnsell_222422025);
		categoryInfo_gz_222422025.put("global", mallInfoUnsell_222422025);
		categoryInfo_gz_222422025.put("retail_global", mallInfoUnsell_222422025);
		categoryInfo_gz_222422025.put("combination_global", mallInfoUnsell_222422025);
		
		Map<String,Map<String,Map<String,String>>> siteData_222422025 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422025.put("", categoryInfo_all_222422025);
		siteData_222422025.put("cd", categoryInfo_all_222422025);
		siteData_222422025.put("bj", categoryInfo_all_222422025);
		siteData_222422025.put("sh", categoryInfo_gz_222422025);
		siteData_222422025.put("gz", categoryInfo_gz_222422025);
		/**
		 * 222422027 : 有一个全站deal，类型为pop_cosmetics,预售售卖，deal可售,库存不分站，但是商城cd/bj可售
		 */
		Map<String,String> dealInfoSell_222422027 = new HashMap<String,String>();
		dealInfoSell_222422027.put("product_id", "222422027");
		dealInfoSell_222422027.put("hash_id", "md222422027hd");
		dealInfoSell_222422027.put("is_on_sale", "1");
			
		Map<String,String> mallInfoUnsell_222422027 = new HashMap<String,String>();
		mallInfoUnsell_222422027.put("mall_id", "222422027");
		mallInfoUnsell_222422027.put("mall_product_name", "mall_product_name_222422027");
		mallInfoUnsell_222422027.put("is_on_sale", "0");
		
		Map<String,Map<String,String>> categoryInfo_all_222422027 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422027.put("product", mallInfoUnsell_222422027);
		categoryInfo_all_222422027.put("pop_cosmetics", dealInfoSell_222422027);
		categoryInfo_all_222422027.put("media", mallInfoUnsell_222422027);
		categoryInfo_all_222422027.put("global", mallInfoUnsell_222422027);
		categoryInfo_all_222422027.put("retail_global", mallInfoUnsell_222422027);
		categoryInfo_all_222422027.put("combination_global", mallInfoUnsell_222422027);	
		Map<String,Map<String,String>> categoryInfo_gz_222422027 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422027.put("product", mallInfoUnsell_222422027);
		categoryInfo_gz_222422027.put("pop_cosmetics", dealInfoSell_222422027);
		categoryInfo_gz_222422027.put("media", mallInfoUnsell_222422027);
		categoryInfo_gz_222422027.put("global", mallInfoUnsell_222422027);
		categoryInfo_gz_222422027.put("retail_global", mallInfoUnsell_222422027);
		categoryInfo_gz_222422027.put("combination_global", mallInfoUnsell_222422027);	
				
		Map<String,Map<String,Map<String,String>>> siteData_222422027 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422027.put("", categoryInfo_all_222422027);
		siteData_222422027.put("cd", categoryInfo_all_222422027);
		siteData_222422027.put("bj", categoryInfo_all_222422027);
		siteData_222422027.put("sh", categoryInfo_gz_222422027);
		siteData_222422027.put("gz", categoryInfo_gz_222422027);
		/**
		 * 222422029 : 有一个全站deal，类型为product,普通售卖，deal可售,库存不分站，但是商城cd/bj可售
		 */
		Map<String,String> dealInfoSell_222422029 = new HashMap<String,String>();
		dealInfoSell_222422029.put("product_id", "222422029");
		dealInfoSell_222422029.put("hash_id", "md222422029hd");
		dealInfoSell_222422029.put("is_on_sale", "1");
		
		Map<String,String> mallInfoUnsell_222422029 = new HashMap<String,String>();
		mallInfoUnsell_222422029.put("mall_id", "222422029");
		mallInfoUnsell_222422029.put("mall_product_name", "mall_product_name_222422029");
		mallInfoUnsell_222422029.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422029 = new HashMap<String,String>();
		mallInfoSell_222422029.put("mall_id", "222422029");
		mallInfoSell_222422029.put("mall_product_name", "mall_product_name_222422029");
		mallInfoSell_222422029.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422029 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422029.put("product", dealInfoSell_222422029);
		categoryInfo_all_222422029.put("pop_cosmetics", mallInfoSell_222422029);
		categoryInfo_all_222422029.put("media", mallInfoSell_222422029);
		categoryInfo_all_222422029.put("global", mallInfoSell_222422029);
		categoryInfo_all_222422029.put("retail_global", mallInfoSell_222422029);
		categoryInfo_all_222422029.put("combination_global", mallInfoSell_222422029);	
		Map<String,Map<String,String>> categoryInfo_gz_222422029 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422029.put("product", dealInfoSell_222422029);
		categoryInfo_gz_222422029.put("pop_cosmetics", mallInfoUnsell_222422029);
		categoryInfo_gz_222422029.put("media", mallInfoUnsell_222422029);
		categoryInfo_gz_222422029.put("global", mallInfoUnsell_222422029);
		categoryInfo_gz_222422029.put("retail_global", mallInfoUnsell_222422029);
		categoryInfo_gz_222422029.put("combination_global", mallInfoUnsell_222422029);	
				
		Map<String,Map<String,Map<String,String>>> siteData_222422029 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422029.put("", categoryInfo_all_222422029);
		siteData_222422029.put("cd", categoryInfo_all_222422029);
		siteData_222422029.put("bj", categoryInfo_all_222422029);
		siteData_222422029.put("sh", categoryInfo_gz_222422029);
		siteData_222422029.put("gz", categoryInfo_gz_222422029);
		
		/**
		 * 222422030 : cd分站deal，类型为product,普通售卖，deal可售,库存分站，但是商城cd/bj可售
		 */
		Map<String,String> dealInfoSell_222422030 = new HashMap<String,String>();
		dealInfoSell_222422030.put("product_id", "222422030");
		dealInfoSell_222422030.put("hash_id", "md222422030hd");
		dealInfoSell_222422030.put("is_on_sale", "1");
		
		Map<String,String> mallInfoUnsell_222422030 = new HashMap<String,String>();
		mallInfoUnsell_222422030.put("mall_id", "222422030");
		mallInfoUnsell_222422030.put("mall_product_name", "mall_product_name_222422030");
		mallInfoUnsell_222422030.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422030 = new HashMap<String,String>();
		mallInfoSell_222422030.put("mall_id", "222422030");
		mallInfoSell_222422030.put("mall_product_name", "mall_product_name_222422030");
		mallInfoSell_222422030.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422030 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422030.put("product", mallInfoSell_222422030);
		categoryInfo_all_222422030.put("pop_cosmetics", mallInfoSell_222422030);
		categoryInfo_all_222422030.put("media", mallInfoSell_222422030);
		categoryInfo_all_222422030.put("global", mallInfoSell_222422030);
		categoryInfo_all_222422030.put("retail_global", mallInfoSell_222422030);
		categoryInfo_all_222422030.put("combination_global", mallInfoSell_222422030);	
		Map<String,Map<String,String>> categoryInfo_gz_222422030 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422030.put("product", mallInfoUnsell_222422030);
		categoryInfo_gz_222422030.put("pop_cosmetics", mallInfoUnsell_222422030);
		categoryInfo_gz_222422030.put("media", mallInfoUnsell_222422030);
		categoryInfo_gz_222422030.put("global", mallInfoUnsell_222422030);
		categoryInfo_gz_222422030.put("retail_global", mallInfoUnsell_222422030);
		categoryInfo_gz_222422030.put("combination_global", mallInfoUnsell_222422030);	
		Map<String,Map<String,String>> categoryInfo_cd_222422030 = new HashMap<String,Map<String,String>>();
		categoryInfo_cd_222422030.put("product", dealInfoSell_222422030);
		categoryInfo_cd_222422030.put("pop_cosmetics", mallInfoSell_222422030);
		categoryInfo_cd_222422030.put("media", mallInfoSell_222422030);
		categoryInfo_cd_222422030.put("global", mallInfoSell_222422030);
		categoryInfo_cd_222422030.put("retail_global", mallInfoSell_222422030);
		categoryInfo_cd_222422030.put("combination_global", mallInfoSell_222422030);	
				
		Map<String,Map<String,Map<String,String>>> siteData_222422030 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422030.put("", categoryInfo_all_222422030);
		siteData_222422030.put("cd", categoryInfo_cd_222422030);
		siteData_222422030.put("bj", categoryInfo_all_222422030);
		siteData_222422030.put("sh", categoryInfo_gz_222422030);
		siteData_222422030.put("gz", categoryInfo_gz_222422030);
		/**
		 * 222422240 : 全站deal，类型为pop_cosmetics,普通售卖，deal可售(cd/bj),库存分站，但是商城不可售
		 */
		Map<String,String> dealInfoSell_222422240 = new HashMap<String,String>();
		dealInfoSell_222422240.put("product_id", "222422240");
		dealInfoSell_222422240.put("hash_id", "md222422240hd");
		dealInfoSell_222422240.put("is_on_sale", "1");
		
		Map<String,String> dealInfoUnsell_222422240 = new HashMap<String,String>();
		dealInfoUnsell_222422240.put("product_id", "222422240");
		dealInfoUnsell_222422240.put("hash_id", "md222422240hd");
		dealInfoUnsell_222422240.put("is_on_sale", "0");
		
		Map<String,String> mallInfoUnsell_222422240 = new HashMap<String,String>();
		mallInfoUnsell_222422240.put("mall_id", "222422240");
		mallInfoUnsell_222422240.put("mall_product_name", "mall_product_name_222422240");
		mallInfoUnsell_222422240.put("is_on_sale", "0");
		
		Map<String,Map<String,String>> categoryInfo_all_222422240 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422240.put("pop_cosmetics", dealInfoSell_222422240);
		categoryInfo_all_222422240.put("product", mallInfoUnsell_222422240);
		categoryInfo_all_222422240.put("media", mallInfoUnsell_222422240);
		categoryInfo_all_222422240.put("global", mallInfoUnsell_222422240);
		categoryInfo_all_222422240.put("retail_global", mallInfoUnsell_222422240);
		categoryInfo_all_222422240.put("combination_global", mallInfoUnsell_222422240);	
		Map<String,Map<String,String>> categoryInfo_gz_222422240 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422240.put("pop_cosmetics", dealInfoUnsell_222422240);
		categoryInfo_gz_222422240.put("product", mallInfoUnsell_222422240);
		categoryInfo_gz_222422240.put("media", mallInfoUnsell_222422240);
		categoryInfo_gz_222422240.put("global", mallInfoUnsell_222422240);
		categoryInfo_gz_222422240.put("retail_global", mallInfoUnsell_222422240);
		categoryInfo_gz_222422240.put("combination_global", mallInfoUnsell_222422240);	
				
		Map<String,Map<String,Map<String,String>>> siteData_222422240 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422240.put("", categoryInfo_all_222422240);
		siteData_222422240.put("cd", categoryInfo_all_222422240);
		siteData_222422240.put("bj", categoryInfo_all_222422240);
		siteData_222422240.put("sh", categoryInfo_gz_222422240);
		siteData_222422240.put("gz", categoryInfo_gz_222422240);
		
		/**
		 * 222422242 : 全站deal，类型为pop_cosmetics,普通售卖，deal不可售 for tpi is_enabled=0,库存分站
		 */
		Map<String,String> dealInfoUnsell_222422242 = new HashMap<String,String>();
		dealInfoUnsell_222422242.put("product_id", "222422242");
		dealInfoUnsell_222422242.put("hash_id", "md222422242hd");
		dealInfoUnsell_222422242.put("is_on_sale", "0");
		
		Map<String,String> mallInfoUnsell_222422242 = new HashMap<String,String>();
		mallInfoUnsell_222422242.put("mall_id", "222422242");
		mallInfoUnsell_222422242.put("mall_product_name", "mall_product_name_222422242");
		mallInfoUnsell_222422242.put("is_on_sale", "0");
		
		Map<String,Map<String,String>> categoryInfo_all_222422242 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422242.put("product", mallInfoUnsell_222422242);
		categoryInfo_all_222422242.put("pop_cosmetics", dealInfoUnsell_222422242);
		categoryInfo_all_222422242.put("media", mallInfoUnsell_222422242);
		categoryInfo_all_222422242.put("global", mallInfoUnsell_222422242);
		categoryInfo_all_222422242.put("retail_global", mallInfoUnsell_222422242);
		categoryInfo_all_222422242.put("combination_global", mallInfoUnsell_222422242);	
		Map<String,Map<String,String>> categoryInfo_gz_222422242 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422242.put("pop_cosmetics", dealInfoUnsell_222422242);
		categoryInfo_gz_222422242.put("product", mallInfoUnsell_222422242);
		categoryInfo_gz_222422242.put("media", mallInfoUnsell_222422242);
		categoryInfo_gz_222422242.put("global", mallInfoUnsell_222422242);
		categoryInfo_gz_222422242.put("retail_global", mallInfoUnsell_222422242);
		categoryInfo_gz_222422242.put("combination_global", mallInfoUnsell_222422242);	
				
		Map<String,Map<String,Map<String,String>>> siteData_222422242 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422242.put("", categoryInfo_all_222422242);
		siteData_222422242.put("cd", categoryInfo_all_222422242);
		siteData_222422242.put("bj", categoryInfo_all_222422242);
		siteData_222422242.put("sh", categoryInfo_gz_222422242);
		siteData_222422242.put("gz", categoryInfo_gz_222422242);
		
		/**
		 * 222422034 : 有一个全站deal和一个cd站deal，类型为product,普通售卖,库存分站，但是商城cd/bj可售
		 */
		Map<String,String> dealInfoUnsell_222422034 = new HashMap<String,String>();
		dealInfoUnsell_222422034.put("product_id", "222422034");
		dealInfoUnsell_222422034.put("hash_id", "md222422034hd_2");
		dealInfoUnsell_222422034.put("is_on_sale", "0");
		
		Map<String,String> dealInfoSell_222422034 = new HashMap<String,String>();
		dealInfoSell_222422034.put("product_id", "222422034");
		dealInfoSell_222422034.put("hash_id", "md222422034hd_2");
		dealInfoSell_222422034.put("is_on_sale", "1");
		
		Map<String,String> mallInfoUnsell_222422034 = new HashMap<String,String>();
		mallInfoUnsell_222422034.put("mall_id", "222422034");
		mallInfoUnsell_222422034.put("mall_product_name", "mall_product_name_222422034");
		mallInfoUnsell_222422034.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422034 = new HashMap<String,String>();
		mallInfoSell_222422034.put("mall_id", "222422034");
		mallInfoSell_222422034.put("mall_product_name", "mall_product_name_222422034");
		mallInfoSell_222422034.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422034 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422034.put("product", dealInfoSell_222422034);
		categoryInfo_all_222422034.put("pop_cosmetics", mallInfoSell_222422034);
		categoryInfo_all_222422034.put("media", mallInfoSell_222422034);
		categoryInfo_all_222422034.put("global", mallInfoSell_222422034);
		categoryInfo_all_222422034.put("retail_global", mallInfoSell_222422034);
		categoryInfo_all_222422034.put("combination_global", mallInfoSell_222422034);	
		Map<String,Map<String,String>> categoryInfo_gz_222422034 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422034.put("product", dealInfoUnsell_222422034);
		categoryInfo_gz_222422034.put("pop_cosmetics", mallInfoUnsell_222422034);
		categoryInfo_gz_222422034.put("media", mallInfoUnsell_222422034);
		categoryInfo_gz_222422034.put("global", mallInfoUnsell_222422034);
		categoryInfo_gz_222422034.put("retail_global", mallInfoUnsell_222422034);
		categoryInfo_gz_222422034.put("combination_global", mallInfoUnsell_222422034);	
				
		Map<String,Map<String,Map<String,String>>> siteData_222422034 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422034.put("", categoryInfo_all_222422034);
		siteData_222422034.put("cd", categoryInfo_all_222422034);
		siteData_222422034.put("bj", categoryInfo_all_222422034);
		siteData_222422034.put("sh", categoryInfo_gz_222422034);
		siteData_222422034.put("gz", categoryInfo_gz_222422034);
		
		/**
		 * 222422243 : 有一个全站deal(status=0)和一个cd站deal(status=1)，类型为product,普通售卖,库存分站，但是商城cd/bj可售
		 */
		Map<String,String> dealInfoUnsell_222422243 = new HashMap<String,String>();
		dealInfoUnsell_222422243.put("product_id", "222422243");
		dealInfoUnsell_222422243.put("hash_id", "md222422243hd_1");
		dealInfoUnsell_222422243.put("is_on_sale", "0");
		
		Map<String,String> dealInfoSell_222422243_cd = new HashMap<String,String>();
		dealInfoSell_222422243_cd.put("product_id", "222422243");
		dealInfoSell_222422243_cd.put("hash_id", "md222422243hd_2");
		dealInfoSell_222422243_cd.put("is_on_sale", "1");
		
		Map<String,String> dealInfoSell_222422243_all = new HashMap<String,String>();
		dealInfoSell_222422243_all.put("product_id", "222422243");
		dealInfoSell_222422243_all.put("hash_id", "md222422243hd_1");
		dealInfoSell_222422243_all.put("is_on_sale", "1");
		
		Map<String,String> mallInfoUnsell_222422243 = new HashMap<String,String>();
		mallInfoUnsell_222422243.put("mall_id", "222422243");
		mallInfoUnsell_222422243.put("mall_product_name", "mall_product_name_222422243");
		mallInfoUnsell_222422243.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422243 = new HashMap<String,String>();
		mallInfoSell_222422243.put("mall_id", "222422243");
		mallInfoSell_222422243.put("mall_product_name", "mall_product_name_222422243");
		mallInfoSell_222422243.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422243 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422243.put("product", dealInfoSell_222422243_all);
		categoryInfo_all_222422243.put("pop_cosmetics", mallInfoSell_222422243);
		categoryInfo_all_222422243.put("media", mallInfoSell_222422243);
		categoryInfo_all_222422243.put("global", mallInfoSell_222422243);
		categoryInfo_all_222422243.put("retail_global", mallInfoSell_222422243);
		categoryInfo_all_222422243.put("combination_global", mallInfoSell_222422243);	
		Map<String,Map<String,String>> categoryInfo_gz_222422243 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422243.put("product", dealInfoUnsell_222422243);
		categoryInfo_gz_222422243.put("pop_cosmetics", mallInfoUnsell_222422243);
		categoryInfo_gz_222422243.put("media", mallInfoUnsell_222422243);
		categoryInfo_gz_222422243.put("global", mallInfoUnsell_222422243);
		categoryInfo_gz_222422243.put("retail_global", mallInfoUnsell_222422243);
		categoryInfo_gz_222422243.put("combination_global", mallInfoUnsell_222422243);
		Map<String,Map<String,String>> categoryInfo_cd_222422243 = new HashMap<String,Map<String,String>>();
		categoryInfo_cd_222422243.put("product", dealInfoSell_222422243_cd);
		categoryInfo_cd_222422243.put("pop_cosmetics", mallInfoSell_222422243);
		categoryInfo_cd_222422243.put("media", mallInfoSell_222422243);
		categoryInfo_cd_222422243.put("global", mallInfoSell_222422243);
		categoryInfo_cd_222422243.put("retail_global", mallInfoSell_222422243);
		categoryInfo_cd_222422243.put("combination_global", mallInfoSell_222422243);	
				
		Map<String,Map<String,Map<String,String>>> siteData_222422243 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422243.put("", categoryInfo_all_222422243);
		siteData_222422243.put("cd", categoryInfo_cd_222422243);
		siteData_222422243.put("bj", categoryInfo_all_222422243);
		siteData_222422243.put("sh", categoryInfo_gz_222422243);
		siteData_222422243.put("gz", categoryInfo_gz_222422243);
		
		/**
		 * 222422244 : 类型为product,普通售卖,库存分站，可售deal和预售deal同时存在，优先可售。商城cd/bj可售
		 */
		Map<String,String> dealInfoPresell_222422244 = new HashMap<String,String>();
		dealInfoPresell_222422244.put("product_id", "222422244");
		dealInfoPresell_222422244.put("hash_id", "md222422244hd_2");
		dealInfoPresell_222422244.put("is_on_sale", "2");
		
		Map<String,String> dealInfoSell_222422244 = new HashMap<String,String>();
		dealInfoSell_222422244.put("product_id", "222422244");
		dealInfoSell_222422244.put("hash_id", "md222422244hd_1");
		dealInfoSell_222422244.put("is_on_sale", "1");
		
		Map<String,String> mallInfoUnsell_222422244 = new HashMap<String,String>();
		mallInfoUnsell_222422244.put("mall_id", "222422244");
		mallInfoUnsell_222422244.put("mall_product_name", "mall_product_name_222422244");
		mallInfoUnsell_222422244.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422244 = new HashMap<String,String>();
		mallInfoSell_222422244.put("mall_id", "222422244");
		mallInfoSell_222422244.put("mall_product_name", "mall_product_name_222422244");
		mallInfoSell_222422244.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422244 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422244.put("product", dealInfoSell_222422244);
		categoryInfo_all_222422244.put("pop_cosmetics", mallInfoSell_222422244);
		categoryInfo_all_222422244.put("media", mallInfoSell_222422244);
		categoryInfo_all_222422244.put("global", mallInfoSell_222422244);
		categoryInfo_all_222422244.put("retail_global", mallInfoSell_222422244);
		categoryInfo_all_222422244.put("combination_global", mallInfoSell_222422244);	
		Map<String,Map<String,String>> categoryInfo_gz_222422244 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422244.put("product", dealInfoPresell_222422244);
		categoryInfo_gz_222422244.put("pop_cosmetics", mallInfoUnsell_222422244);
		categoryInfo_gz_222422244.put("media", mallInfoUnsell_222422244);
		categoryInfo_gz_222422244.put("global", mallInfoUnsell_222422244);
		categoryInfo_gz_222422244.put("retail_global", mallInfoUnsell_222422244);
		categoryInfo_gz_222422244.put("combination_global", mallInfoUnsell_222422244);
				
		Map<String,Map<String,Map<String,String>>> siteData_222422244 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422244.put("", categoryInfo_all_222422244);
		siteData_222422244.put("cd", categoryInfo_all_222422244);
		siteData_222422244.put("bj", categoryInfo_all_222422244);
		siteData_222422244.put("sh", categoryInfo_gz_222422244);
		siteData_222422244.put("gz", categoryInfo_gz_222422244);
		
		/**
		 * 222422245 : 类型为product和pop_cosmetics,普通售卖,库存分站。商城cd/bj可售.不可售deal优先不可售mall
		 */
		Map<String,String> dealInfoUnsell_222422245_pop_cosmetics = new HashMap<String,String>();
		dealInfoUnsell_222422245_pop_cosmetics.put("product_id", "222422245");
		dealInfoUnsell_222422245_pop_cosmetics.put("hash_id", "md222422245hd_1");
		dealInfoUnsell_222422245_pop_cosmetics.put("is_on_sale", "0");
		
		Map<String,String> dealInfoUnsell_222422245_product = new HashMap<String,String>();
		dealInfoUnsell_222422245_product.put("product_id", "222422245");
		dealInfoUnsell_222422245_product.put("hash_id", "md222422245hd_2");
		dealInfoUnsell_222422245_product.put("is_on_sale", "0");
		
		Map<String,String> mallInfoUnsell_222422245 = new HashMap<String,String>();
		mallInfoUnsell_222422245.put("mall_id", "222422245");
		mallInfoUnsell_222422245.put("mall_product_name", "mall_product_name_222422245");
		mallInfoUnsell_222422245.put("is_on_sale", "0");
		
		Map<String,Map<String,String>> categoryInfo_gz_222422245 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422245.put("product", dealInfoUnsell_222422245_product);
		categoryInfo_gz_222422245.put("pop_cosmetics", dealInfoUnsell_222422245_pop_cosmetics);
		categoryInfo_gz_222422245.put("media", mallInfoUnsell_222422245);
		categoryInfo_gz_222422245.put("global", mallInfoUnsell_222422245);
		categoryInfo_gz_222422245.put("retail_global", mallInfoUnsell_222422245);
		categoryInfo_gz_222422245.put("combination_global", mallInfoUnsell_222422245);
				
		Map<String,Map<String,Map<String,String>>> siteData_222422245 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422245.put("", categoryInfo_gz_222422245);
		siteData_222422245.put("cd", categoryInfo_gz_222422245);
		siteData_222422245.put("bj", categoryInfo_gz_222422245);
		siteData_222422245.put("sh", categoryInfo_gz_222422245);
		siteData_222422245.put("gz", categoryInfo_gz_222422245);
		
		/**
		 * 222422250 : 类型为product,普通售卖,库存分站。商城cd/bj可售.预售deal优先不可售mall
		 */
		Map<String,String> dealInfoUnsell_222422250 = new HashMap<String,String>();
		dealInfoUnsell_222422250.put("product_id", "222422250");
		dealInfoUnsell_222422250.put("hash_id", "md222422250hd_2");
		dealInfoUnsell_222422250.put("is_on_sale", "0");
		
		Map<String,String> dealInfoPresell_222422250 = new HashMap<String,String>();
		dealInfoPresell_222422250.put("product_id", "222422250");
		dealInfoPresell_222422250.put("hash_id", "md222422250hd_1");
		dealInfoPresell_222422250.put("is_on_sale", "2");
		
		Map<String,String> dealInfoSell_222422250 = new HashMap<String,String>();
		dealInfoSell_222422250.put("product_id", "222422250");
		dealInfoSell_222422250.put("hash_id", "md222422250hd_2");
		dealInfoSell_222422250.put("is_on_sale", "1");
		
		Map<String,String> mallInfoUnsell_222422250 = new HashMap<String,String>();
		mallInfoUnsell_222422250.put("mall_id", "222422250");
		mallInfoUnsell_222422250.put("mall_product_name", "mall_product_name_222422250");
		mallInfoUnsell_222422250.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422250 = new HashMap<String,String>();
		mallInfoSell_222422250.put("mall_id", "222422250");
		mallInfoSell_222422250.put("mall_product_name", "mall_product_name_222422250");
		mallInfoSell_222422250.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422250 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422250.put("product", dealInfoSell_222422250);
		categoryInfo_all_222422250.put("pop_cosmetics", mallInfoSell_222422250);
		categoryInfo_all_222422250.put("media", mallInfoSell_222422250);
		categoryInfo_all_222422250.put("global", mallInfoSell_222422250);
		categoryInfo_all_222422250.put("retail_global", mallInfoSell_222422250);
		categoryInfo_all_222422250.put("combination_global", mallInfoSell_222422250);
		
		Map<String,Map<String,String>> categoryInfo_gz_222422250 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422250.put("product", dealInfoPresell_222422250);
		categoryInfo_gz_222422250.put("pop_cosmetics", mallInfoUnsell_222422250);
		categoryInfo_gz_222422250.put("media", mallInfoUnsell_222422250);
		categoryInfo_gz_222422250.put("global", mallInfoUnsell_222422250);
		categoryInfo_gz_222422250.put("retail_global", mallInfoUnsell_222422250);
		categoryInfo_gz_222422250.put("combination_global", mallInfoUnsell_222422250);
		
		Map<String,Map<String,String>> categoryInfo_sh_222422250 = new HashMap<String,Map<String,String>>();
		categoryInfo_sh_222422250.put("product", dealInfoUnsell_222422250);
		categoryInfo_sh_222422250.put("pop_cosmetics", mallInfoUnsell_222422250);
		categoryInfo_sh_222422250.put("media", mallInfoUnsell_222422250);
		categoryInfo_sh_222422250.put("global", mallInfoUnsell_222422250);
		categoryInfo_sh_222422250.put("retail_global", mallInfoUnsell_222422250);
		categoryInfo_sh_222422250.put("combination_global", mallInfoUnsell_222422250);
				
		Map<String,Map<String,Map<String,String>>> siteData_222422250 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422250.put("", categoryInfo_all_222422250);
		siteData_222422250.put("cd", categoryInfo_all_222422250);
		siteData_222422250.put("bj", categoryInfo_all_222422250);
		siteData_222422250.put("sh", categoryInfo_sh_222422250);
		siteData_222422250.put("gz", categoryInfo_gz_222422250);
		
		/**
		 * 222422251 : 类型为product,site = '',普通售卖,库存分站。商城cd/bj可售.优先选取开始时间最近的deal
		 */
		Map<String,String> dealInfoUnsell_222422251 = new HashMap<String,String>();
		dealInfoUnsell_222422251.put("product_id", "222422251");
		dealInfoUnsell_222422251.put("hash_id", "md222422251hd_2");
		dealInfoUnsell_222422251.put("is_on_sale", "0");
		
		Map<String,String> dealInfoSell_222422251 = new HashMap<String,String>();
		dealInfoSell_222422251.put("product_id", "222422251");
		dealInfoSell_222422251.put("hash_id", "md222422251hd_2");
		dealInfoSell_222422251.put("is_on_sale", "1");
		
		Map<String,String> mallInfoUnsell_222422251 = new HashMap<String,String>();
		mallInfoUnsell_222422251.put("mall_id", "222422251");
		mallInfoUnsell_222422251.put("mall_product_name", "mall_product_name_222422251");
		mallInfoUnsell_222422251.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422251 = new HashMap<String,String>();
		mallInfoSell_222422251.put("mall_id", "222422251");
		mallInfoSell_222422251.put("mall_product_name", "mall_product_name_222422251");
		mallInfoSell_222422251.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422251 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422251.put("product", dealInfoSell_222422251);
		categoryInfo_all_222422251.put("pop_cosmetics", mallInfoSell_222422251);
		categoryInfo_all_222422251.put("media", mallInfoSell_222422251);
		categoryInfo_all_222422251.put("global", mallInfoSell_222422251);
		categoryInfo_all_222422251.put("retail_global", mallInfoSell_222422251);
		categoryInfo_all_222422251.put("combination_global", mallInfoSell_222422251);
		
		Map<String,Map<String,String>> categoryInfo_gz_222422251 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422251.put("product", dealInfoUnsell_222422251);
		categoryInfo_gz_222422251.put("pop_cosmetics", mallInfoUnsell_222422251);
		categoryInfo_gz_222422251.put("media", mallInfoUnsell_222422251);
		categoryInfo_gz_222422251.put("global", mallInfoUnsell_222422251);
		categoryInfo_gz_222422251.put("retail_global", mallInfoUnsell_222422251);
		categoryInfo_gz_222422251.put("combination_global", mallInfoUnsell_222422251);
		
				
		Map<String,Map<String,Map<String,String>>> siteData_222422251 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422251.put("", categoryInfo_all_222422251);
		siteData_222422251.put("cd", categoryInfo_all_222422251);
		siteData_222422251.put("bj", categoryInfo_all_222422251);
		siteData_222422251.put("sh", categoryInfo_gz_222422251);
		siteData_222422251.put("gz", categoryInfo_gz_222422251);
		
		/**
		 * 222422252 : 类型为product,site = '',普通售卖,库存分站。商城cd/bj可售.优先选取开始时间最近的deal
		 */
		Map<String,String> dealInfoUnsell_222422252 = new HashMap<String,String>();
		dealInfoUnsell_222422252.put("product_id", "222422252");
		dealInfoUnsell_222422252.put("hash_id", "md222422252hd_1");
		dealInfoUnsell_222422252.put("is_on_sale", "0");
		
		Map<String,String> dealInfoSell_222422252 = new HashMap<String,String>();
		dealInfoSell_222422252.put("product_id", "222422252");
		dealInfoSell_222422252.put("hash_id", "md222422252hd_1");
		dealInfoSell_222422252.put("is_on_sale", "1");
		
		Map<String,String> mallInfoUnsell_222422252 = new HashMap<String,String>();
		mallInfoUnsell_222422252.put("mall_id", "222422252");
		mallInfoUnsell_222422252.put("mall_product_name", "mall_product_name_222422252");
		mallInfoUnsell_222422252.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422252 = new HashMap<String,String>();
		mallInfoSell_222422252.put("mall_id", "222422252");
		mallInfoSell_222422252.put("mall_product_name", "mall_product_name_222422252");
		mallInfoSell_222422252.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422252 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422252.put("product", dealInfoSell_222422252);
		categoryInfo_all_222422252.put("pop_cosmetics", mallInfoSell_222422252);
		categoryInfo_all_222422252.put("media", mallInfoSell_222422252);
		categoryInfo_all_222422252.put("global", mallInfoSell_222422252);
		categoryInfo_all_222422252.put("retail_global", mallInfoSell_222422252);
		categoryInfo_all_222422252.put("combination_global", mallInfoSell_222422252);
		
		Map<String,Map<String,String>> categoryInfo_gz_222422252 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422252.put("product", dealInfoUnsell_222422252);
		categoryInfo_gz_222422252.put("pop_cosmetics", mallInfoUnsell_222422252);
		categoryInfo_gz_222422252.put("media", mallInfoUnsell_222422252);
		categoryInfo_gz_222422252.put("global", mallInfoUnsell_222422252);
		categoryInfo_gz_222422252.put("retail_global", mallInfoUnsell_222422252);
		categoryInfo_gz_222422252.put("combination_global", mallInfoUnsell_222422252);
		
				
		Map<String,Map<String,Map<String,String>>> siteData_222422252 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422252.put("", categoryInfo_all_222422252);
		siteData_222422252.put("cd", categoryInfo_all_222422252);
		siteData_222422252.put("bj", categoryInfo_all_222422252);
		siteData_222422252.put("sh", categoryInfo_gz_222422252);
		siteData_222422252.put("gz", categoryInfo_gz_222422252);
		
		return new Object[][]{
				{"[222422024]",siteData_222422024},
				{"[222422025]",siteData_222422025},
				{"[222422027]",siteData_222422027},
				{"[222422029]",siteData_222422029},
				{"[222422030]",siteData_222422030},
				{"[222422240]",siteData_222422240},
				{"[222422242]",siteData_222422242},
				{"[222422034]",siteData_222422034},
				{"[222422243]",siteData_222422243},
				{"[222422244]",siteData_222422244},
				{"[222422245]",siteData_222422245},
				{"[222422250]",siteData_222422250},
				{"[222422251]",siteData_222422251},
				{"[222422252]",siteData_222422252},
		};
	}
	
	@DataProvider(name = "data_WriteDataToSaleTable")
	public static Object[][] dataWriteDataToSaleTable(){
		/**
		 * 商城和团购都售卖的产品
		 */
		Map<String,Map<String,String>> expectedEndTimeAndStatus_222422253 = new HashMap<String,Map<String,String>>();
		//初始化时间
		Map<String,String> expectedStatus_222422253 = new HashMap<String,String>();
		expectedStatus_222422253.put("status", "0");
		expectedStatus_222422253.put("endTime", "1463587200");
		expectedEndTimeAndStatus_222422253.put("all", expectedStatus_222422253);
		expectedEndTimeAndStatus_222422253.put("bj", expectedStatus_222422253);
		expectedEndTimeAndStatus_222422253.put("gz", expectedStatus_222422253);
		expectedEndTimeAndStatus_222422253.put("sh", expectedStatus_222422253);
		expectedStatus_222422253 = new HashMap<String,String>();
		expectedStatus_222422253.put("status", "0");
		expectedStatus_222422253.put("endTime", "1462723200");
		expectedEndTimeAndStatus_222422253.put("cd", expectedStatus_222422253);
		//初始化具体数据
		Map<String,String> dealInfoUnsell_222422253_all = new HashMap<String,String>();
		dealInfoUnsell_222422253_all.put("product_id", "222422253");
		dealInfoUnsell_222422253_all.put("hash_id", "md222422253hd_2");
		dealInfoUnsell_222422253_all.put("is_on_sale", "0");
		Map<String,String> dealInfoSell_222422253_all = new HashMap<String,String>();
		dealInfoSell_222422253_all.put("product_id", "222422253");
		dealInfoSell_222422253_all.put("hash_id", "md222422253hd_2");
		dealInfoSell_222422253_all.put("other_on_sale_deal", "{\"md222422253hd_1\":{\"product_id\":\"222422253\",\"hash_id\":\"md222422253hd_1\",\"is_on_sale\":1}}");
		dealInfoSell_222422253_all.put("is_on_sale", "1");
		
		Map<String,String> dealInfoSell_222422253_cd = new HashMap<String,String>();
		dealInfoSell_222422253_cd.put("product_id", "222422253");
		dealInfoSell_222422253_cd.put("hash_id", "md222422253hd_3");
		dealInfoSell_222422253_cd.put("is_on_sale", "1");
		
		Map<String,String> mallInfoUnsell_222422253 = new HashMap<String,String>();
		mallInfoUnsell_222422253.put("mall_id", "222422253");
		mallInfoUnsell_222422253.put("mall_product_name", "mall_product_name_222422253");
		mallInfoUnsell_222422253.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222422253 = new HashMap<String,String>();
		mallInfoSell_222422253.put("mall_id", "222422253");
		mallInfoSell_222422253.put("mall_product_name", "mall_product_name_222422253");
		mallInfoSell_222422253.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222422253 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422253.put("product", dealInfoSell_222422253_all);
		categoryInfo_all_222422253.put("mall", mallInfoSell_222422253);
		
		Map<String,Map<String,String>> categoryInfo_cd_222422253 = new HashMap<String,Map<String,String>>();
		categoryInfo_cd_222422253.put("product", dealInfoSell_222422253_all);
		categoryInfo_cd_222422253.put("pop_cosmetics", dealInfoSell_222422253_cd);
		categoryInfo_cd_222422253.put("mall", mallInfoSell_222422253);
		
		Map<String,Map<String,String>> categoryInfo_gz_222422253 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222422253.put("product", dealInfoUnsell_222422253_all);
		categoryInfo_gz_222422253.put("mall", mallInfoUnsell_222422253);
		
				
		Map<String,Map<String,Map<String,String>>> siteData_222422253 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422253.put("all", categoryInfo_all_222422253);
		siteData_222422253.put("cd", categoryInfo_cd_222422253);
		siteData_222422253.put("bj", categoryInfo_all_222422253);
		siteData_222422253.put("sh", categoryInfo_gz_222422253);
		siteData_222422253.put("gz", categoryInfo_gz_222422253);
		
		/**
		 * 只卖商城产品
		 */
		Map<String,Map<String,String>> expectedEndTimeAndStatus_222421994 = new HashMap<String,Map<String,String>>();
		//初始化时间
		Map<String,String> expectedStatus_222421994 = new HashMap<String,String>();
		expectedStatus_222421994.put("status", "-1");
		expectedStatus_222421994.put("endTime", "0");
		expectedEndTimeAndStatus_222421994.put("all", expectedStatus_222421994);
		expectedEndTimeAndStatus_222421994.put("bj", expectedStatus_222421994);
		expectedEndTimeAndStatus_222421994.put("gz", expectedStatus_222421994);
		expectedEndTimeAndStatus_222421994.put("sh", expectedStatus_222421994);
		expectedEndTimeAndStatus_222421994.put("cd", expectedStatus_222421994);
		
		Map<String,String> mallInfoUnsell_222421994 = new HashMap<String,String>();
		mallInfoUnsell_222421994.put("mall_id", "222421994");
		mallInfoUnsell_222421994.put("mall_product_name", "mall_product_name_only_here_1");
		mallInfoUnsell_222421994.put("is_on_sale", "0");
		
		Map<String,String> mallInfoSell_222421994 = new HashMap<String,String>();
		mallInfoSell_222421994.put("mall_id", "222421994");
		mallInfoSell_222421994.put("mall_product_name", "mall_product_name_only_here_1");
		mallInfoSell_222421994.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222421994 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222421994.put("mall", mallInfoSell_222421994);
		
		Map<String,Map<String,String>> categoryInfo_gz_222421994 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222421994.put("mall", mallInfoUnsell_222421994);
		
		Map<String,Map<String,Map<String,String>>> siteData_222421994 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222421994.put("all", categoryInfo_all_222421994);
		siteData_222421994.put("cd", categoryInfo_all_222421994);
		siteData_222421994.put("bj", categoryInfo_all_222421994);
		siteData_222421994.put("sh", categoryInfo_gz_222421994);
		siteData_222421994.put("gz", categoryInfo_gz_222421994);
		/**
		 * 只卖团购产品
		 */
		Map<String,Map<String,String>> expectedEndTimeAndStatus_222421996 = new HashMap<String,Map<String,String>>();
		//初始化时间
		Map<String,String> expectedStatus_222421996 = new HashMap<String,String>();
		expectedStatus_222421996.put("status", "1");
		expectedStatus_222421996.put("endTime", "1467043200");
		expectedEndTimeAndStatus_222421996.put("all", expectedStatus_222421996);
		expectedEndTimeAndStatus_222421996.put("bj", expectedStatus_222421996);
		expectedEndTimeAndStatus_222421996.put("gz", expectedStatus_222421996);
		expectedEndTimeAndStatus_222421996.put("sh", expectedStatus_222421996);
		expectedEndTimeAndStatus_222421996.put("cd", expectedStatus_222421996);
		
		Map<String,String> dealInfoUnsell_222421996 = new HashMap<String,String>();
		dealInfoUnsell_222421996.put("product_id", "222421996");
		dealInfoUnsell_222421996.put("hash_id", "md222421996hd");
		dealInfoUnsell_222421996.put("is_on_sale", "0");
		
		Map<String,String> dealInfoSell_222421996 = new HashMap<String,String>();
		dealInfoSell_222421996.put("product_id", "222421996");
		dealInfoSell_222421996.put("hash_id", "md222421996hd");
		dealInfoSell_222421996.put("is_on_sale", "1");
		
		Map<String,Map<String,String>> categoryInfo_all_222421996 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222421996.put("product", dealInfoSell_222421996);
		
		Map<String,Map<String,String>> categoryInfo_gz_222421996 = new HashMap<String,Map<String,String>>();
		categoryInfo_gz_222421996.put("product", dealInfoUnsell_222421996);
		
		Map<String,Map<String,Map<String,String>>> siteData_222421996 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222421996.put("all", categoryInfo_all_222421996);
		siteData_222421996.put("cd", categoryInfo_all_222421996);
		siteData_222421996.put("bj", categoryInfo_all_222421996);
		siteData_222421996.put("sh", categoryInfo_gz_222421996);
		siteData_222421996.put("gz", categoryInfo_gz_222421996);
		
		/**
		 * 团购和商城都售卖，deal状态为0或1才会写xx_end_time,xx_status
		 */
		Map<String,Map<String,String>> expectedEndTimeAndStatus_222422260 = new HashMap<String,Map<String,String>>();
		//初始化时间
		Map<String,String> expectedStatus_222422260 = new HashMap<String,String>();
		expectedStatus_222422260.put("status", "-1");
		expectedStatus_222422260.put("endTime", "0");
		expectedEndTimeAndStatus_222422260.put("all", expectedStatus_222422260);
		expectedEndTimeAndStatus_222422260.put("bj", expectedStatus_222422260);
		expectedEndTimeAndStatus_222422260.put("gz", expectedStatus_222422260);
		expectedEndTimeAndStatus_222422260.put("sh", expectedStatus_222422260);
		expectedEndTimeAndStatus_222422260.put("cd", expectedStatus_222422260);
		
		Map<String,String> dealInfoUnsell_222422260 = new HashMap<String,String>();
		dealInfoUnsell_222422260.put("product_id", "222422260");
		dealInfoUnsell_222422260.put("hash_id", "md222422260hd");
		dealInfoUnsell_222422260.put("is_on_sale", "0");
		
		Map<String,Map<String,String>> categoryInfo_all_222422260 = new HashMap<String,Map<String,String>>();
		categoryInfo_all_222422260.put("media", dealInfoUnsell_222422260);
		
		Map<String,Map<String,Map<String,String>>> siteData_222422260 = new HashMap<String,Map<String,Map<String,String>>>();
		siteData_222422260.put("all", categoryInfo_all_222422260);
		siteData_222422260.put("cd", categoryInfo_all_222422260);
		siteData_222422260.put("bj", categoryInfo_all_222422260);
		siteData_222422260.put("sh", categoryInfo_all_222422260);
		siteData_222422260.put("gz", categoryInfo_all_222422260);
		return new Object[][]{
				{"[222422253]",expectedEndTimeAndStatus_222422253,siteData_222422253},
				{"[222421994]",expectedEndTimeAndStatus_222421994,siteData_222421994},
				{"[222421996]",expectedEndTimeAndStatus_222421996,siteData_222421996},
				{"[222422260]",expectedEndTimeAndStatus_222422260,siteData_222422260}
		};
	}
	
	@DataProvider(name = "data_GetRecommendsForShop")
	public static Object[][] dataGetRecommendsForShop(){
		/**
		 * 仅自营商城售卖,cd/bj下可售
		 */
		Map<String,Map<String,String>> expectedResult_222421994 = new HashMap<String,Map<String,String>>();
		Map<String,String> detail_222421994 = new HashMap<String,String>();
		detail_222421994.put("hash_id", "");
		detail_222421994.put("mall_id", "222421994");
		detail_222421994.put("discounted_price", "15.50");
		detail_222421994.put("original_price", "12.30");
		detail_222421994.put("category_v3_3", "13");
		detail_222421994.put("brand_id", "2");
		detail_222421994.put("name", "product_short_name only sell in mall_1");
		detail_222421994.put("mall_sku", "[\"sku222421994\"]");
		detail_222421994.put("mall_warehouse", "[\"CD06\",\"TJ11\"]");
		expectedResult_222421994.put("222421994", detail_222421994);
		
		Map<String,String> detail_222422030_deal = new HashMap<String,String>();
		detail_222422030_deal.put("hash_id", "md222422030hd");
		detail_222422030_deal.put("mall_id", "");
		detail_222422030_deal.put("discounted_price", "13.1");
		detail_222422030_deal.put("original_price", "12.1");
		detail_222422030_deal.put("category_v3_3", "13");
		detail_222422030_deal.put("brand_id", "2");
		detail_222422030_deal.put("name", "medium_name");
		expectedResult_222421994.put("222422030", detail_222422030_deal);
		
		/**
		 * 仅团购售卖产品,category='product',site = '', status = 1, 库存分站， cd/bj站有库存,
		 * 仅团购售卖产品,category='product',site = '', status = 0, 库存分站， cd/bj站有库存,
		
		 */
		Map<String,Map<String,String>> expectedResult_222421996 = new HashMap<String,Map<String,String>>();
		Map<String,String> detail_222421996_deal = new HashMap<String,String>();
		detail_222421996_deal.put("hash_id", "md222421996hd");
		detail_222421996_deal.put("mall_id", "");
		detail_222421996_deal.put("discounted_price", "13.1");
		detail_222421996_deal.put("original_price", "12.1");
		detail_222421996_deal.put("category_v3_3", "0");
		detail_222421996_deal.put("brand_id", "2");
		detail_222421996_deal.put("name", "medium_name");
		expectedResult_222421996.put("222421996", detail_222421996_deal);
		
		
		/**
		 * 仅团购售卖产品, categroy='product',site = '', status = 1, 库存不分站， cd/bj站有库存,
		 * 仅团购售卖产品,category='media',site = '', status = 1, 库存不分站
		 */
		Map<String,Map<String,String>> expectedResult_222422012 = new HashMap<String,Map<String,String>>();
		Map<String,String> detail_222422012_deal = new HashMap<String,String>();
		detail_222422012_deal.put("hash_id", "md222422012hd");
		detail_222422012_deal.put("mall_id", "");
		detail_222422012_deal.put("discounted_price", "13.1");
		detail_222422012_deal.put("original_price", "12.1");
		detail_222422012_deal.put("category_v3_3", "0");
		detail_222422012_deal.put("brand_id", "2");
		detail_222422012_deal.put("name", "medium_name");
		expectedResult_222422012.put("222422012", detail_222422012_deal);
		
		/**
		 * 仅团购售卖产品,site = '', status = 1, 库存不分站， cd/bj站有库存, 即将开售
		 */
		/**
		 * 仅团购售卖产品,site = '', status = 0, 库存分站， cd/bj站有库存, 即将开售
		 */
		
		/**
		 * 仅团购和商城都售卖产品,site = 'cd', status = 1, cd/bj站有库存
		 */
		Map<String,Map<String,String>> expectedResult_222422030_bj = new HashMap<String,Map<String,String>>();
		Map<String,String> detail_222422030_mall = new HashMap<String,String>();
		detail_222422030_mall.put("hash_id", "");
		detail_222422030_mall.put("mall_id", "222422030");
		detail_222422030_mall.put("discounted_price", "124.20");
		detail_222422030_mall.put("original_price", "12.30");
		detail_222422030_mall.put("category_v3_3", "13");
		detail_222422030_mall.put("brand_id", "2");
		detail_222422030_mall.put("name", "product_short_name_dealAndMall_both_sell_5");
		detail_222422030_mall.put("mall_sku", "[\"sku222422030\"]");
		detail_222422030_mall.put("mall_warehouse", "[\"CD06\",\"TJ11\"]");
		expectedResult_222422030_bj.put("222422030", detail_222422030_mall);
		/**
		 * 仅团购和商城都售卖产品,site = '', status = 1,库存分站, cd/bj站有库存
		 */
		/**
		 * 仅团购和商城都售卖产品,site = '', status = 0,库存分站, cd/bj站有库存
		 */
		/**
		 * 仅团购和商城都售卖产品,site = '', status = 1,库存分站, cd/bj站有库存,即将开售
		 */
		return new Object[][]{
				{"[222421994,222422000,222422030]","cd",expectedResult_222421994},
				{"[222421996,222422013]","",expectedResult_222421996},
				{"[222422012,222422010]","gz",expectedResult_222422012},
				{"[222422030]","bj",expectedResult_222422030_bj},
		};
	}
	
	@DataProvider(name = "data_GetOnSaleDataByProductIds")
	public static Object[][] dataGetOnSaleDataByProductIds(){
		/**
		 * 仅团购售卖产品
		 */
		Map<String,Map<String,String>> expectedResult_222421994 = new HashMap<String,Map<String,String>>();
		Map<String,String> detail_222421994 = new HashMap<String,String>();
		detail_222421994.put("mall_id", "222421994");
		detail_222421994.put("mall_product_name", "mall_product_name_only_here_1");
		detail_222421994.put("show_status", "1");
		detail_222421994.put("category_id", "13");
		detail_222421994.put("brand_id", "2");
		detail_222421994.put("is_on_sale", "1");
		detail_222421994.put("mall_skus", "[\"sku222421994\"]");
		detail_222421994.put("mall_warehouse", "[\"CD06\",\"TJ11\"]");
		expectedResult_222421994.put("222421994", detail_222421994);
		
		Map<String,String> detail_222422030 = new HashMap<String,String>();
		detail_222422030.put("deal_id", "4648777");
		detail_222422030.put("product_id", "222422030");
		detail_222422030.put("hash_id", "md222422030hd");
		detail_222422030.put("sku_attribute", "attribute");
		detail_222422030.put("sku_no", "sku222422030");
		detail_222422030.put("is_on_sale", "1");
		expectedResult_222421994.put("222422030", detail_222422030);
		return new Object[][]{
				{"[222421994,222422030]","cd","",expectedResult_222421994},
		};
	}
}



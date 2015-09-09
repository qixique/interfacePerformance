package userinf.test;

import interfacePerformance.db.QueryHelper;
import interfacePerformance.util.BasicPortal;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CollectionCenterTest {
	
	static List<String> sites = null;
	static List<String> categories = null;
	static List<String> mallFileds = null;
	static List<String> dealFileds = null;
	Connection conn = null;
	
	//��ʼ��վ��ͷ�����Ϣ
	static{
		sites = new ArrayList<String>();
		sites.add("");
		sites.add("cd");
		sites.add("bj");
		sites.add("gz");
		sites.add("sh");
		
		categories = new ArrayList<String>();
		categories.add("product");
		categories.add("media");
		categories.add("global");
		categories.add("pop_cosmetics");
		categories.add("retail_global");
		categories.add("combination_global");
		
		String[] mallFiledsArray = {"product_id","mall_id","mall_product_name","market_price","mall_price","show_status","discount","start_time","end_time","is_enabled","is_shown_in_list","is_new","is_hot","is_shown_in_zones","is_free_shipping","buyer_number","real_buyer_number","user_purchase_limit","show_tag","sale_price","sale_start_time","sale_end_time","fake_30day_buyer_number","real_30day_buyer_number","gold_member_price","platinum_member_price","diamond_member_price","company","is_on_sale","product_short_name","product_report_rating","deal_comments_number","category_id","brand_id","product_reports_number"};
		mallFileds = Arrays.asList(mallFiledsArray);
		
		String[] dealFiledsArray = {"deal_id","product_id","hash_id","site","stocks","real_buyer_number","name","need_sync","category","short_name","shipping_system_id","status","discounted_price","original_price","discount","buyer_number","start_time","end_time","is_published_price","sku_attribute","sku_no","is_on_sale"};
		dealFileds = Arrays.asList(dealFiledsArray);
	}
	
//	@BeforeClass
//	public void beforeClass() throws SQLException{
//		Map<String,List<String>> sqlMap = FileHelper.getSqlFromFile("sql.txt");
//		List<String> delSql = sqlMap.get("delete");
//		List<String> insertSql = sqlMap.get("insert");
//		if(delSql.size() > 0 || insertSql.size() > 0){
//			conn = QueryHelper.getPrivateConn("jdbc:mysql://192.168.20.71:9001/jumei_product", "dev", "jmdevcd");
//			Statement st = conn.createStatement();
//			if(delSql.size() > 0){
//				for(String sql : delSql){
//					st.addBatch(sql);
//				}
//				st.executeBatch();
//			}
//			st.clearBatch();
//			if(insertSql.size() > 0){
//				for(String sql : insertSql){
//					st.addBatch(sql);
//				}
//				st.executeBatch();
//			}
//			st.close();
//		}
//	}
	
	@AfterClass
	public void afterClass() throws SQLException{
		if(conn != null){
			conn.close();
		}
	}
	
	@DataProvider(name = "data_OnlyMallSaledProductId")
	public Object[][] dataOnlyMallSaledProductId(){
		List<String> sites_1 = new ArrayList<String>();
		sites_1.add("");
		sites_1.add("bj");
		sites_1.add("cd");
		List<String> sites_2 = new ArrayList<String>();
		sites_2.add("");
		sites_2.add("bj");
		sites_2.add("gz");
		return new Object[][]{{"[222421994]",sites_1,false},{"[222422000]",sites_2,false},{"[222422001]",new ArrayList<String>(),false},{"[222422002]",null,true}};
	}
	
	/**
	 * ���̳������Ĳ�Ʒ���������
	 * @param product_id 222421994
	 * @param isSaleable
	 * @param isGlobalMall
	 */
//	@Test(dataProvider = "data_OnlyMallSaledProductId")
	public void testOnlyMallSaledProductId(String product_ids, List<String> sellableSite, boolean isEmpty){
		Assert.assertNotNull(product_ids,"product_ids����Ϊ��");
		Assert.assertNotNull(isEmpty,"isGlobalMall����Ϊ��");
		try {
			BasicPortal bp=new BasicPortal();
			String result=bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + product_ids + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"�ӿڵ��÷����쳣");
			JSONObject jsonResult = new JSONObject(result);
			//������֯�ṹ���
			this.checkDataFormat(jsonResult, product_ids);
			Map<String,Map<String,Map<String,String>>> datas = this.parseData(jsonResult);
			//�����ղؽӿ��߼�û���ҵ����ݣ�Ϊ��ʱ���
			if(isEmpty){
				this.checkDataEmpty(datas,categories);
			}else{
				//�����̳���Ϣʱ��productId�����ݼ��
				this.checkMallFullIn(datas, sellableSite);
			}
		} catch (Exception e) {
			Assert.fail("����ʱ,�����쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@DataProvider(name = "data_OnlyMallSaledProductIdDetailValue")
	public Object[][] dataOnlyMallSaledProductIdDetailValue(){
		Map<String,Map<String,String>> expectedResult_1 = new HashMap<String,Map<String,String>>();
		Map<String,String> data = new HashMap<String,String>();
		data.put("product_id", "222421994");
		data.put("mall_product_name", "mall_product_name_only_here_1");
		data.put("category_id", "13");
		data.put("product_short_name", "product_short_name only sell in mall_1");
		data.put("mall_skus", "\"sku222421994\"");
		data.put("mall_warehouse", "\"CD06\",\"TJ11\"");
		expectedResult_1.put("222421994", data);
		return new Object[][]{{"[222421994]",expectedResult_1}};
	}
	
	/**
	 * �̳����ݼ��
	 * @param productIds
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_OnlyMallSaledProductIdDetailValue")
	public void testOnlyMallSaledProductIdDetailValue(String productIds, Map<String,Map<String,String>> expectedResult){
		Assert.assertNotNull(productIds,"productIds����Ϊ��");
		Assert.assertNotNull(expectedResult, "expectedResult����Ϊ�ա�");
		try {
			BasicPortal bp=new BasicPortal();
			String result=bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"�ӿڵ��÷����쳣");
			JSONObject jsonResult = new JSONObject(result);
			Map<String,Map<String,Map<String,String>>> datas = this.parseData(jsonResult);
			
			String[] productIdsArray = productIds.replace("[", "").replace("]", "").split(",");
			//��鴫���product_id������ӿڷ��ص�product_id����һ��
			Assert.assertEquals(productIdsArray.length, datas.keySet().size(),"�����productId������ӿڷ��صĸ�����һ�¡�");
			String categoryValue = "";
			JSONObject categoryObject = null;
			Map<String,Map<String,String>> siteData = null;
			Map<String,String> categoryData = null;
			Map<String,String> filedsData = null;
			Map<String,String> expectedFiledsData = null;
			String[] mall_skus = null;
			String[] mall_warehouses = null;
			String[] expected_mall_skus = null;
			String[] expected_mall_warehouses = null;
			for(String productId : productIdsArray){
				siteData = datas.get(productId);
				for(String site : sites){
					categoryData = siteData.get(site);
					for(String category : categories){
						categoryValue = categoryData.get(category);
						categoryObject = new JSONObject(categoryValue);
						filedsData = this.convertJSONObjectToMap(categoryObject);
						
						//�����ֶμ��
						for(String f : mallFileds){
							Assert.assertTrue(filedsData.containsKey(f),productId + "��,վ��" + site + "�µ�����" + category + "��,û���ҵ�" + f + "�ֶ�");
						}
						//��鷵���ֶθ����Ƿ���ȷ
						if(categoryObject.getString("is_on_sale").equals("1")){
							Assert.assertEquals(filedsData.size(), mallFileds.size() + 2,productId + "��,վ��" + site + "�µ�����" + category + "��,�����ֶθ���������������һ�¡�");
							//mall_skus��mall_warehouse�ֶμ��
							mall_skus = filedsData.get("mall_skus").replace("[", "").replace("]", "").split(",");
							mall_warehouses = filedsData.get("mall_warehouse").replace("[", "").replace("]", "").split(",");
							expected_mall_skus = expectedResult.get(productId).get("mall_skus").split(",");
							expected_mall_warehouses = expectedResult.get(productId).get("mall_warehouse").split(",");
							Assert.assertEquals(mall_skus.length, expected_mall_skus.length,productId + "��,վ��" + site + "�µ�'mall_skus'�ֶη���ֵ������һ�¡�");
							Assert.assertEquals(mall_warehouses.length, expected_mall_warehouses.length,productId + "��,վ��" + site + "�µ�'mall_warehouse'�ֶη���ֵ������һ�¡�");
							Arrays.sort(mall_skus);
							Arrays.sort(expected_mall_skus);
							Assert.assertTrue(Arrays.equals(mall_skus, expected_mall_skus),productId + "��,վ��" + site + "�µ�'mall_skus'�ֶη���ֵ������һ�¡�");
							Arrays.sort(mall_warehouses);
							Arrays.sort(expected_mall_warehouses);
							Assert.assertTrue(Arrays.equals(mall_warehouses, expected_mall_warehouses),productId + "��,վ��" + site + "�µ�'mall_warehouse'�ֶη���ֵ������һ�¡�");
						}else{
							Assert.assertEquals(filedsData.size(), mallFileds.size(),productId + "��,վ��" + site + "�µ�����" + category + "��,�����ֶθ���������������һ�¡�");
						}
						//���������ֶμ��
						expectedFiledsData = expectedResult.get(productId);
						for(String k : expectedFiledsData.keySet()){
							if(k.equals("mall_skus") || k.equals("mall_warehouse"))
								continue;
							Assert.assertEquals(filedsData.get(k), expectedFiledsData.get(k),productId + "��,վ��" + site + "�µ�'" + k + "'�ֶη���ֵ������һ�¡�");
						}
					}
				}
			}
		}catch (Exception e) {
			Assert.fail("����ʱ,�����쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@DataProvider(name = "data_OnlyDealSaledProductId")
	public Object[][] dataOnlyDealSaledProductId(){
		/**
		 * category : media
		 */
		List<String> mediaCategories = Arrays.asList(new String[]{"media"});
		/**
		 * category : product
		 */
		List<String> productCategories = Arrays.asList(new String[]{"product"});
		List<String> productSellabeSites = Arrays.asList(new String[]{"","cd","bj"});
		/**
		 * category : pop_cosmetics
		 */
		List<String> popCategories = Arrays.asList(new String[]{"pop_cosmetics"});
		List<String> popSellabeSites = Arrays.asList(new String[]{"","gz","sh"});
		/**
		 * category : global
		 */
		List<String> globalCategories = Arrays.asList(new String[]{"global"});
		/**
		 * category : retail_global
		 */
		List<String> retailGlobalCategories = Arrays.asList(new String[]{"retail_global"});
		/**
		 * category : combination_global
		 */
		List<String> comGlobalCategories = Arrays.asList(new String[]{"combination_global"});
		/**
		 * category : pop_cosmetics mall��show_status=3
		 */
		List<String> popMallCategories = Arrays.asList(new String[]{"pop_cosmetics"});
		List<String> popMallSellabeSites = Arrays.asList(new String[]{"","cd","bj"});
		return new Object[][]{
				{"[222421995]",sites,mediaCategories,"1"},{"[222422010]",sites,mediaCategories,"1"},{"[222422011]",new ArrayList<String>(),mediaCategories,"0"},
				{"[222421996]",productSellabeSites,productCategories,"1"},{"[222422012]",sites,productCategories,"1"},{"[222422013]",productSellabeSites,productCategories,"1"},{"[222422015]",sites,productCategories,"2"},{"[222422014]",new ArrayList<String>(),productCategories,"0"},
				{"[222422016]",popSellabeSites,popCategories,"1"},
				{"[222422017]",sites,globalCategories,"1"},{"[222422018]",sites,globalCategories,"1"},
				{"[222422019]",sites,retailGlobalCategories,"1"},{"[222422020]",sites,retailGlobalCategories,"1"},{"[222422021]",sites,retailGlobalCategories,"2"},{"[222422023]",sites,retailGlobalCategories,"1"},
				{"[222422022]",sites,comGlobalCategories,"1"},
				{"[222422241]",popMallSellabeSites,popMallCategories,"1"},
			};
	}
	
	/**
	 * �������Ź���Ʒ���
	 * @param product_id
	 * @param isSaleable
	 */
	@Test(dataProvider = "data_OnlyDealSaledProductId")
	public void testOnlyDealSaledProductId(String productIds,List<String> sellableSite,List<String> sellabelCategories,String dealSellabeStatus){
		Assert.assertNotNull(productIds,"productIds����Ϊ��");
		Assert.assertNotNull(sellableSite,"sellableSite����Ϊ��");
		Assert.assertNotNull(sellabelCategories,"sellableCategories����Ϊ��");
		Assert.assertNotNull(dealSellabeStatus,"dealSellableStatus����Ϊ��");
		try {
			BasicPortal bp=new BasicPortal();
			String result = bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"�ӿڵ��÷����쳣");
			JSONObject jsonResult = new JSONObject(result);
			Map<String,Map<String,Map<String,String>>> datas = this.parseData(jsonResult);
			//վ���£���Ӧ����û�к���dealʱ��Ϊ�ռ��
			List<String> copyCategories = new ArrayList<String>();
			for(String s : categories){
				if(sellabelCategories.contains(s))
					continue;
				copyCategories.add(s);
			}
			this.checkDataEmpty(datas,copyCategories);
			
			String productId = productIds.replace("[", "").replace("]", "");
			Map<String,Map<String,String>> siteDatas = datas.get(productId);
			Map<String,String> categoriesDatas = null;
			Map<String,String> dealDatas = null;
			String category = null;
			for(String s : sites){
				categoriesDatas = siteDatas.get(s);
				if(sellableSite.contains(s)){
					for(String c : sellabelCategories){
						category = categoriesDatas.get(c);
						dealDatas = this.convertJSONObjectToMap(new JSONObject(category));
						Assert.assertEquals(dealDatas.get("is_on_sale"), dealSellabeStatus,productId + "��,վ��" + s + "��,����" + c + "�Ŀ���״̬����ȷ��");
					}
				}else{
					for(String c : sellabelCategories){
						category = categoriesDatas.get(c);
						dealDatas = this.convertJSONObjectToMap(new JSONObject(category));
						Assert.assertEquals(dealDatas.get("is_on_sale"), "0",productId + "��,վ��" + s + "��,����" + c + "�Ŀ���״̬����ȷ��");
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("����ʱ,�����쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	@DataProvider(name = "data_OnlyDealSaledProductIdDetailValue")
	public Object[][] dataOnlyDealSaledProductIdDetailValue(){
		/**
		 * ��ͨdeal��Ϣ���:media
		 */
		Map<String,String> mediaDeal = new HashMap<String,String>();
		mediaDeal.put("product_id", "222421995");
		mediaDeal.put("hash_id", "md222421995hd");
		mediaDeal.put("category", "media");
		mediaDeal.put("is_published_price", "1");
		mediaDeal.put("real_buyer_number", "0");
		mediaDeal.put("discounted_price", "13.1");
		mediaDeal.put("original_price", "12.1");
		mediaDeal.put("discount", "2.3");
		mediaDeal.put("sku_attribute", "sku_attribute_1,sku_attribute_2");
		mediaDeal.put("sku_no", "sku222421995_1,sku222421995_2");
		mediaDeal.put("dealOnSale_site", "[\"bj\",\"cd\",\"sh\",\"gz\",\"\"]");
		
		/**
		 * ��ͨdeal��Ϣ���:product
		 */
		Map<String,String> productDeal = new HashMap<String,String>();
		productDeal.put("product_id", "222421996");
		productDeal.put("hash_id", "md222421996hd");
		productDeal.put("category", "product");
		productDeal.put("is_published_price", "2");
		productDeal.put("buyer_number", "12");
		productDeal.put("discounted_price", "13.1");
		productDeal.put("original_price", "12.1");
		productDeal.put("discount", "2.3");
		productDeal.put("sku_attribute", "attribute");
		productDeal.put("sku_no", "sku222421996");
		productDeal.put("dealOnSale_site", "[\"bj\",\"cd\",\"\"]");
		
		/**
		 * ��ͨdeal��Ϣ���:global
		 */
		Map<String,String> globalDeal = new HashMap<String,String>();
		globalDeal.put("product_id", "222422017");
		globalDeal.put("hash_id", "md222422017hd");
		globalDeal.put("category", "global");
		globalDeal.put("is_published_price", "3");
		globalDeal.put("buyer_number", "12");
		globalDeal.put("discounted_price", "120.00");
		globalDeal.put("original_price", "200.00");
		globalDeal.put("discount", "6.0");
		globalDeal.put("sku_attribute", "g_attribute");
		globalDeal.put("sku_no", "sku222422017");
		globalDeal.put("dealOnSale_site", "[\"bj\",\"cd\",\"sh\",\"gz\",\"\"]");
		
		/**
		 * ��ͨdeal��Ϣ���:retail_global
		 */
		Map<String,String> retailGlobalDeal = new HashMap<String,String>();
		retailGlobalDeal.put("product_id", "222422019");
		retailGlobalDeal.put("hash_id", "md222422019hd");
		retailGlobalDeal.put("category", "retail_global");
		retailGlobalDeal.put("is_published_price", "1");
		retailGlobalDeal.put("buyer_number", "12");
		retailGlobalDeal.put("discounted_price", "120.00");
		retailGlobalDeal.put("original_price", "200.00");
		retailGlobalDeal.put("discount", "6.0");
		retailGlobalDeal.put("sku_attribute", "rg_attribute");
		retailGlobalDeal.put("sku_no", "sku222422019");
		retailGlobalDeal.put("dealOnSale_site", "[\"bj\",\"cd\",\"sh\",\"gz\",\"\"]");
		
		/**
		 * ��ͨdeal��Ϣ���:combination_global
		 */
		Map<String,String> combinationGlobalDeal = new HashMap<String,String>();
		combinationGlobalDeal.put("product_id", "222422022");
		combinationGlobalDeal.put("hash_id", "md222422022hd");
		combinationGlobalDeal.put("category", "combination_global");
		combinationGlobalDeal.put("is_published_price", "1");
		combinationGlobalDeal.put("buyer_number", "12");
		combinationGlobalDeal.put("discounted_price", "120.00");
		combinationGlobalDeal.put("original_price", "200.00");
		combinationGlobalDeal.put("discount", "6.0");
		combinationGlobalDeal.put("sku_attribute", "rg_attribute");
		combinationGlobalDeal.put("sku_no", "sku222422022");
		combinationGlobalDeal.put("dealOnSale_site", "[\"bj\",\"cd\",\"sh\",\"gz\",\"\"]");
		
		return new Object[][]{
				{"[222421995]",Arrays.asList(new String[] {"media"}),mediaDeal},
				{"[222421996]",Arrays.asList(new String[] {"product"}),productDeal},
				{"[222422017]",Arrays.asList(new String[] {"global"}),globalDeal},
				{"[222422019]",Arrays.asList(new String[] {"retail_global"}),retailGlobalDeal},
				{"[222422022]",Arrays.asList(new String[] {"combination_global"}),combinationGlobalDeal}
		};
	}
	
	/**
	 * ������վ�㣬����Ŀ¼��deal����ϸ��Ϣ
	 * @param productIds
	 * @param sellabelCategories
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_OnlyDealSaledProductIdDetailValue")
	public void testOnlyDealSaledProductIdDetailValue(String productIds,List<String> sellabelCategories,Map<String,String> expectedResult){
		Assert.assertNotNull(productIds,"productIds����Ϊ��");
		Assert.assertNotNull(sellabelCategories,"sellableCategories����Ϊ��");
		Assert.assertNotNull(expectedResult,"expectedResult����Ϊ��");
		try {
			BasicPortal bp=new BasicPortal();
			String result = bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"�ӿڵ��÷����쳣");
			JSONObject jsonResult = new JSONObject(result);
			Map<String,Map<String,Map<String,String>>> datas = this.parseData(jsonResult);
			String productId = productIds.replace("[", "").replace("]", "");
			Map<String,Map<String,String>> siteDatas = datas.get(productId);
			Map<String,String> categoryDatas = null;
			Map<String,String> dealData = null;
			Set<String> key = null;
			for(String s : sites){
				categoryDatas = siteDatas.get(s);
				for(String c : sellabelCategories){
					dealData = this.convertJSONObjectToMap(new JSONObject(categoryDatas.get(c)));
					if(!dealData.get("is_on_sale").equals("1")){
						key = expectedResult.keySet();
						key.remove("dealOnSale_site");
						for(String compareFiled : key){
							Assert.assertEquals(dealData.get(compareFiled), expectedResult.get(compareFiled),productId + "�У�վ��" + s + "��,����" + c + "'��deal��'" + compareFiled + "'�ֶαȶԴ���");
						}
						//dealOnSale_site�ֶμ��
						Assert.assertEquals(dealData.get("dealOnSale_site"), "[\"\",\"cd\",\"gz\",\"sh\",\"bj\"]",productId + "�У�վ��" + s + "��,����" + c + "'��deal��'dealOnSale_site'�ֶαȶԴ���");
					}else{
						for(String compareFiled : expectedResult.keySet()){
							Assert.assertEquals(dealData.get(compareFiled), expectedResult.get(compareFiled),productId + "�У�վ��" + s + "��,����" + c + "'��deal��'" + compareFiled + "'�ֶαȶԴ���");
						}
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("����ʱ,�����쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * �̳Ǻ�deal�������Ĳ�Ʒ�����
	 * @param product_id
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_DealAndMallSaledProductId",dataProviderClass=CollectionCenterTestData.class)
	public void testDealAndMallSaledProductId(String productIds, Map<String,Map<String,Map<String,String>>> expectedResult){
		Assert.assertNotNull(productIds,"productIds����Ϊ��");
		Assert.assertNotNull(expectedResult,"expectedResult����Ϊ��");
		try{
			BasicPortal bp=new BasicPortal();
			String result = bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"�ӿڵ��÷����쳣");
			JSONObject jsonResult = new JSONObject(result);
			Map<String,Map<String,Map<String,String>>> datas = this.parseData(jsonResult);
			String productId = productIds.replace("[", "").replace("]", "");
			Map<String,Map<String,String>> siteDatas = datas.get(productId);
			Map<String,String> categoryDatas = null;
			Map<String,String> detailData = null;
			Map<String,String> expectedMap = null;
			String expectedValue = "";
			
			for(String s : sites){
				categoryDatas = siteDatas.get(s);
				for(String c : categories){
					detailData = this.convertJSONObjectToMap(new JSONObject(categoryDatas.get(c)));
					expectedMap = expectedResult.get(s).get(c);
					for(String key : expectedMap.keySet()){
						expectedValue = expectedMap.get(key);
						//����Ƿ����ҵ��ض��ķ��عؼ��ֶ�
						Assert.assertNotNull(detailData.get(key) , productId + "��,վ��" + s + "��,����'" + c + "'��û���ҵ������ֶ�'" + key + "'��");
						Assert.assertEquals(detailData.get(key), expectedValue , productId + "��,վ��" + s + "��,����'" + c + "'�е�'" + key + "'�ȶԴ���");
					}
				}
			}
		}catch (Exception e) {
			Assert.fail("����ʱ,�����쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ����д�ղر�
	 * @param productIds
	 * @param expectedEndTimeAndStatus
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_WriteDataToSaleTable",dataProviderClass=CollectionCenterTestData.class)
	public void testWriteDataToSaleTable(String productId,Map<String,Map<String,String>> expectedEndTimeAndStatus, Map<String,Map<String,Map<String,String>>> expectedResult){
		Assert.assertNotNull(productId,"productIds����Ϊ��");
		Assert.assertNotNull(expectedEndTimeAndStatus,"expectedEndTimeAndStatus����Ϊ��");
		Assert.assertNotNull(expectedResult,"expectedResult����Ϊ��");
		try {
			BasicPortal bp=new BasicPortal();
			String result = bp.exe("JumeiProduct_Write_ProductOnSale","updateByProductId",new JSONArray("[" + productId + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"�ӿڵ��÷����쳣");
			productId = productId.replace("[", "").replace("]", "");
			Assert.assertEquals(result, "true",productId + "�����ղر��ɹ���");
			Map<String,Map<String,String>> dbDatas = this.getSaleDataInDb(productId);
			Assert.assertNotNull(dbDatas,productId + "��ѯ���ݿ����");
			Assert.assertTrue(dbDatas.size() > 0,productId + "��ѯ���ݿ����");
			Map<String,String> datas = null;
			Map<String,String> siteData = null;
			Set<String> expectedDealCategoryKey = null;
			Map<String,String> dbCategoryInfo = null;
			Map<String,String> expectedCategoryInfo = null;
			Map<String,String> acutalOther = null;
			Map<String,String> expectedOther = null;
			Map<String,String> expectedDealInfo = null;
			Map<String,String> actualDealInfo = null;
			Map<String,String> actualMall = null;
			Map<String,String> expectedMall = null;
			int mallFlag = 0;
			int expectMallCount = 0;
			String fullMallCategory = "";
			for(String site : dbDatas.keySet()){
				datas = dbDatas.get(site);
				//ʱ��Ƚ�
				Assert.assertEquals(datas.get("endTime"), expectedEndTimeAndStatus.get(site).get("endTime"),productId + "��,վ��" + site + "��,ʱ��ȶԴ���.");
				//״̬�ȶ�
				Assert.assertEquals(datas.get("status"),expectedEndTimeAndStatus.get(site).get("status"),productId + "��,վ��" + site + "��,״̬�ȶԴ���.");
				//����ֵ�Ƚ�
				siteData = this.convertJSONObjectToMap(new JSONObject(datas.get("data")));
				expectedDealCategoryKey = expectedResult.get(site).keySet();
				mallFlag = 0;
				fullMallCategory = "";
				for(String c : categories){
					if(expectedDealCategoryKey.contains(c)){
						dbCategoryInfo = this.convertJSONObjectToMap(new JSONObject(siteData.get(c)));
						expectedCategoryInfo = expectedResult.get(site).get(c);
						for(String k : expectedCategoryInfo.keySet()){
							if(k.equals("other_on_sale_deal")){
								acutalOther = this.convertJSONObjectToMap(new JSONObject(dbCategoryInfo.get(k)));
								expectedOther = this.convertJSONObjectToMap(new JSONObject(expectedCategoryInfo.get(k)));
								for(String hashId : expectedOther.keySet()){
									expectedDealInfo = this.convertJSONObjectToMap(new JSONObject(expectedOther.get(hashId)));
									actualDealInfo = this.convertJSONObjectToMap(new JSONObject(acutalOther.get(hashId)));
									for(String field : expectedDealInfo.keySet()){
										Assert.assertEquals(actualDealInfo.get(field), expectedDealInfo.get(field), productId + "��,վ��" + site + "��,����'" + c + "'��,�ֶ�'" + k + "'ֵ�ȶԴ���:" + hashId + "��" + field +"�ȶԴ���");
									}
								}
							}else{
								Assert.assertEquals(dbCategoryInfo.get(k), expectedCategoryInfo.get(k),productId + "��,վ��" + site + "��,����'" + c + "'��,�ֶ�'" + k + "'ֵ�ȶԴ���");
							}
						}
					}else{
						if(!siteData.get(c).equals("[]")){
							mallFlag++;
							fullMallCategory = c;
						}
					}
				}
				//���mall���
				expectedMall = expectedResult.get(site).get("mall");
				expectMallCount = expectedMall == null ? 0 : 1;
				Assert.assertEquals(mallFlag, expectMallCount,productId + "��,վ��" + site + "��mall��Ϣ������");
				if(expectMallCount == 1){
					//mall����ֵ���
					actualMall = this.convertJSONObjectToMap(new JSONObject(siteData.get(fullMallCategory)));
					for(String field : expectedMall.keySet()){
						Assert.assertEquals(actualMall.get(field), expectedMall.get(field),productId + "��,վ��" + site + "��,����'" + fullMallCategory + "'��,�ֶ�'" + field + "'ֵ�ȶԴ���");
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("����ʱ,�����쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * �û������ղؽӿڲ���
	 * @param productIds
	 * @param site
	 * @param categories
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_GetOnSaleDataByProductIds",dataProviderClass=CollectionCenterTestData.class)
	public void testGetOnSaleDataByProductIds(String productIds, String site, String categories, Map<String,Map<String,String>> expectedResult){
		Assert.assertNotNull(productIds,"productIds����Ϊ��");
		Assert.assertNotNull(site,"site����Ϊ��");
		Assert.assertNotNull(categories,"categories����Ϊ��");
		Assert.assertNotNull(expectedResult,"expectedResult����Ϊ��");
		try{
			BasicPortal bp=new BasicPortal();
			//���ø��½ӿڽ��ղ�����д���ղر�
			String result = bp.exe("JumeiProduct_Write_ProductOnSale","updateByProductId",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"�����ղر�ӿڵ��÷����쳣");
			Assert.assertEquals(result, "true",productIds + "�����ղر��ɹ���");
			//�����û������ղؽӿ�,�����ղ�����
			result = bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleDataByProductIds",new JSONArray("[" + productIds + "," + site + "," + categories + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"�û������ղؽӿڵ��÷����쳣");
			Map<String,String> returnDatas = this.convertJSONObjectToMap(new JSONObject(result));
			Assert.assertEquals(returnDatas.size(), expectedResult.size(),productIds + "���ؽ������������������һ�¡�");
			Map<String,String> detailInfo = null;
			Map<String,String> expectedInfo = null;
			for(String proId : expectedResult.keySet()){
				detailInfo = this.convertJSONObjectToMap(new JSONObject(returnDatas.get(proId)));
				expectedInfo = expectedResult.get(proId);
				for(String key : expectedInfo.keySet()){
					Assert.assertEquals(detailInfo.get(key), expectedInfo.get(key),proId + "��,�ֶ�'" + key + "'������");
				}
			}
		} catch (Exception e) {
			Assert.fail("����ʱ,�����쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ﳵ�·��Ƽ�
	 * @param productIds
	 * @param site
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_GetRecommendsForShop",dataProviderClass=CollectionCenterTestData.class)
	public void testGetRecommendsForShop(String productIds, String site, Map<String,Map<String,String>> expectedResult){
		Assert.assertNotNull(productIds,"productIds����Ϊ��");
		Assert.assertNotNull(site,"site����Ϊ��");
		Assert.assertNotNull(expectedResult,"expectedResult����Ϊ��");
		try{
			BasicPortal bp=new BasicPortal();
			//���ø��½ӿڽ��ղ�����д���ղر�
			String result = bp.exe("JumeiProduct_Write_ProductOnSale","updateByProductId",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"�����ղر�ӿڵ��÷����쳣");
			Assert.assertEquals(result, "true",productIds + "�����ղر��ɹ���");
			//���ù��ﳵ�ղؽӿ�,�����ղ�����
			result = bp.exe("JumeiProduct_Read_Recommend","getRecommendsForShop",new JSONArray("[" + productIds + "," + site + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"���ﳵ�ղؽӿڵ��÷����쳣");
			Map<String,String> returnDatas = this.convertJSONObjectToMap(new JSONObject(result));
			Assert.assertEquals(returnDatas.size(), expectedResult.size(),productIds + "���ؽ������������������һ�¡�");
			for(String proId : expectedResult.keySet()){
				Map<String,String> detailInfo = this.convertJSONObjectToMap(new JSONObject(returnDatas.get(proId)));
				Map<String,String> expectedInfo = expectedResult.get(proId);
				for(String key : expectedInfo.keySet()){
					Assert.assertEquals(detailInfo.get(key), expectedInfo.get(key),proId + "��,�ֶ�'" + key + "'������");
				}
			}
		}catch (Exception e) {
			Assert.fail("����ʱ,�����쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * ���product_id�µ����ݽṹ
	 * @param result
	 * @throws JSONException 
	 */
	private void checkDataFormat(JSONObject result,String product_ids) throws JSONException{
		String[] productIds = product_ids.replace("[", "").replace("]", "").split(",");
		JSONObject productResult = null;
		JSONObject siteResult = null;
		Iterator<String> ite = null;
		String site = null;
		String category = null;
		for(String productId : productIds){
			productResult = result.getJSONObject(productId);
			//��������Ӧ��վ����Ϣ�ǲ��ǵ���5
			Assert.assertEquals(productResult.length(), 5,productId + "��Ʒ�¶�Ӧվ�㲻��5����");
			//վ��ֵ���
			ite = productResult.keys();
			while(ite.hasNext()){
				site = ite.next();
				if(!sites.contains(site)){
					Assert.fail(productId + "���ҵ��Ƿ�վ��:"+site);
				}
			}
			//����վ����category���
			for(String s : sites){
				siteResult = productResult.getJSONObject(s);
				//ÿ��վ���µ�category���
				ite = siteResult.keys();
				while(ite.hasNext()){
					category = ite.next();
					if(!categories.contains(category)){
						Assert.fail(productId + "��,վ��" + s + "���ҵ��Ƿ�category:" + category);
					}
				}
			}
		}
	}
	
	/**
	 * �������ص�json,ת����java����
	 * @param result
	 * @throws JSONException 
	 */
	private Map<String,Map<String,Map<String,String>>> parseData(JSONObject result) throws JSONException{
		Iterator<String> ite_productId = result.keys();
		Iterator<String> ite_site = null;
		Iterator<String> ite_category = null;
		JSONObject siteResult = null;
		JSONObject categoryResult = null;
		String productId = null;
		String site = null;
		String category = null;
		Map<String,Map<String,Map<String,String>>> productData = new HashMap<String,Map<String,Map<String,String>>>();
		Map<String,Map<String,String>> siteData = null;
		Map<String,String> categoryData = null;
		while(ite_productId.hasNext()){
			productId = ite_productId.next();
			siteData = new HashMap<String,Map<String,String>>();
			productData.put(productId, siteData);
			siteResult = result.getJSONObject(productId);
			ite_site = siteResult.keys();
			while(ite_site.hasNext()){
				site = ite_site.next();
				categoryData = new HashMap<String,String>();
				siteData.put(site, categoryData);
				categoryResult = siteResult.getJSONObject(site);
				ite_category = categoryResult.keys();
				while(ite_category.hasNext()){
					category = ite_category.next();
					categoryData.put(category, categoryResult.getString(category));
				}
			}
		}
		return productData;
	}
	/**
	 * ���product_id�¶�Ӧվ��������category���ǿ�
	 * @param datas
	 */
	private void checkDataEmpty(Map<String,Map<String,Map<String,String>>> datas,List<String> emptyCategories){
		String categoryValue = "";
		Map<String,String> categoryData = null;
		for(String productId : datas.keySet()){
			Map<String,Map<String,String>> siteData = datas.get(productId);
			for(String site : sites){
				categoryData = siteData.get(site);
				for(String category : emptyCategories){
					categoryValue = categoryData.get(category);
					if(!categoryValue.equals("[]")){
						Assert.fail(productId + "�У�վ��" + site + "�µ�" + category + "���Ͳ�Ϊ�ա�");
					}
				}
			}
		}
		
	}
	
	/**
	 * ���̳�����ʱ��������ݼ��
	 * @param datas
	 * @param sellableSite
	 * @throws JSONException 
	 */
	private void checkMallFullIn(Map<String,Map<String,Map<String,String>>> datas,List<String> sellableSite) throws JSONException{
		String categoryValue = "";
		JSONObject categoryObject = null;
		Map<String,Map<String,String>> siteData = null;
		Map<String,String> categoryData = null;
		for(String productId : datas.keySet()){
			siteData = datas.get(productId);
			for(String site : sites){
				categoryData = siteData.get(site);
				if(sellableSite.contains(site)){
					for(String category : categories){
						categoryValue = categoryData.get(category);
						categoryObject = new JSONObject(categoryValue);
						Assert.assertEquals(categoryObject.getString("is_on_sale"), "1",productId + "��,վ��" + site + "�µ�����" + category + "û��Ϊ����.");
					}
				}else{
					for(String category : categories){
						categoryValue = categoryData.get(category);
						categoryObject = new JSONObject(categoryValue);
						Assert.assertEquals(categoryObject.getString("is_on_sale"), "0",productId + "��,վ��" + site + "�µ�����" + category + "û��Ϊ������.");
					}
				}
			}
		}
	}
	
	/**
	 * ��json����ת����map����
	 * @param orignal
	 * @return
	 * @throws JSONException 
	 */
	private Map<String,String> convertJSONObjectToMap(JSONObject orignal) throws JSONException{
		Map<String,String> result = new HashMap<String,String>();
		Iterator<String> ite = orignal.keys();
		String filed = null;
		while(ite.hasNext()){
			filed = ite.next();
			result.put(filed, orignal.getString(filed));
		}
		return result;
	}
	
	/**
	 * ���ݲ�Ʒid���ղر��в�ѯ����
	 * @param productId
	 * @return
	 * @throws SQLException
	 */
	private Map<String,Map<String,String>> getSaleDataInDb(String productId) throws SQLException{
		if(conn == null){
			conn = QueryHelper.getPrivateConn("jdbc:mysql://192.168.20.71:9001/jumei_product", "dev", "jmdevcd");
		}
		String sql = "select all_end_time,all_status,all_data,cd_end_time,cd_status,cd_data,bj_end_time,bj_status,bj_data,sh_end_time,sh_status,sh_data,gz_end_time,gz_status,gz_data from jumei_product_on_sale where product_id = " + productId;
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		Map<String,Map<String,String>> resultMap = new HashMap<String,Map<String,String>>();
		Map<String,String> tempMap = null;
		while(rs.next()){
			tempMap = new HashMap<String,String>();
			resultMap.put("all", tempMap);
			tempMap.put("endTime", rs.getString("all_end_time"));
			tempMap.put("status", rs.getString("all_status"));
			tempMap.put("data",rs.getString("all_data"));
			
			tempMap = new HashMap<String,String>();
			resultMap.put("cd", tempMap);
			tempMap.put("endTime", rs.getString("cd_end_time"));
			tempMap.put("status", rs.getString("cd_status"));
			tempMap.put("data",rs.getString("cd_data"));
			
			tempMap = new HashMap<String,String>();
			resultMap.put("bj", tempMap);
			tempMap.put("endTime", rs.getString("bj_end_time"));
			tempMap.put("status", rs.getString("bj_status"));
			tempMap.put("data",rs.getString("bj_data"));
			
			tempMap = new HashMap<String,String>();
			resultMap.put("sh", tempMap);
			tempMap.put("endTime", rs.getString("sh_end_time"));
			tempMap.put("status", rs.getString("sh_status"));
			tempMap.put("data",rs.getString("sh_data"));
			
			tempMap = new HashMap<String,String>();
			resultMap.put("gz", tempMap);
			tempMap.put("endTime", rs.getString("gz_end_time"));
			tempMap.put("status", rs.getString("gz_status"));
			tempMap.put("data",rs.getString("gz_data"));
		}
		st.close();
		rs.close();
		return resultMap;
	}
}

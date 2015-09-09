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
	
	//初始化站点和分类信息
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
	 * 仅商城售卖的产品的数据填充
	 * @param product_id 222421994
	 * @param isSaleable
	 * @param isGlobalMall
	 */
//	@Test(dataProvider = "data_OnlyMallSaledProductId")
	public void testOnlyMallSaledProductId(String product_ids, List<String> sellableSite, boolean isEmpty){
		Assert.assertNotNull(product_ids,"product_ids不能为空");
		Assert.assertNotNull(isEmpty,"isGlobalMall不能为空");
		try {
			BasicPortal bp=new BasicPortal();
			String result=bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + product_ids + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"接口调用返回异常");
			JSONObject jsonResult = new JSONObject(result);
			//数据组织结构检查
			this.checkDataFormat(jsonResult, product_ids);
			Map<String,Map<String,Map<String,String>>> datas = this.parseData(jsonResult);
			//根据收藏接口逻辑没有找到数据，为空时检查
			if(isEmpty){
				this.checkDataEmpty(datas,categories);
			}else{
				//当有商城信息时，productId下数据检查
				this.checkMallFullIn(datas, sellableSite);
			}
		} catch (Exception e) {
			Assert.fail("测试时,发生异常:"+e.getMessage());
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
	 * 商城数据检查
	 * @param productIds
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_OnlyMallSaledProductIdDetailValue")
	public void testOnlyMallSaledProductIdDetailValue(String productIds, Map<String,Map<String,String>> expectedResult){
		Assert.assertNotNull(productIds,"productIds不能为空");
		Assert.assertNotNull(expectedResult, "expectedResult不能为空。");
		try {
			BasicPortal bp=new BasicPortal();
			String result=bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"接口调用返回异常");
			JSONObject jsonResult = new JSONObject(result);
			Map<String,Map<String,Map<String,String>>> datas = this.parseData(jsonResult);
			
			String[] productIdsArray = productIds.replace("[", "").replace("]", "").split(",");
			//检查传入的product_id个数与接口返回的product_id个数一致
			Assert.assertEquals(productIdsArray.length, datas.keySet().size(),"传入的productId个数与接口返回的个数不一致。");
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
						
						//返回字段检查
						for(String f : mallFileds){
							Assert.assertTrue(filedsData.containsKey(f),productId + "中,站点" + site + "下的类型" + category + "中,没有找到" + f + "字段");
						}
						//检查返回字段个数是否正确
						if(categoryObject.getString("is_on_sale").equals("1")){
							Assert.assertEquals(filedsData.size(), mallFileds.size() + 2,productId + "中,站点" + site + "下的类型" + category + "中,返回字段个数于期望个数不一致。");
							//mall_skus和mall_warehouse字段检查
							mall_skus = filedsData.get("mall_skus").replace("[", "").replace("]", "").split(",");
							mall_warehouses = filedsData.get("mall_warehouse").replace("[", "").replace("]", "").split(",");
							expected_mall_skus = expectedResult.get(productId).get("mall_skus").split(",");
							expected_mall_warehouses = expectedResult.get(productId).get("mall_warehouse").split(",");
							Assert.assertEquals(mall_skus.length, expected_mall_skus.length,productId + "中,站点" + site + "下的'mall_skus'字段返回值期望不一致。");
							Assert.assertEquals(mall_warehouses.length, expected_mall_warehouses.length,productId + "中,站点" + site + "下的'mall_warehouse'字段返回值期望不一致。");
							Arrays.sort(mall_skus);
							Arrays.sort(expected_mall_skus);
							Assert.assertTrue(Arrays.equals(mall_skus, expected_mall_skus),productId + "中,站点" + site + "下的'mall_skus'字段返回值期望不一致。");
							Arrays.sort(mall_warehouses);
							Arrays.sort(expected_mall_warehouses);
							Assert.assertTrue(Arrays.equals(mall_warehouses, expected_mall_warehouses),productId + "中,站点" + site + "下的'mall_warehouse'字段返回值期望不一致。");
						}else{
							Assert.assertEquals(filedsData.size(), mallFileds.size(),productId + "中,站点" + site + "下的类型" + category + "中,返回字段个数于期望个数不一致。");
						}
						//其他抽样字段检查
						expectedFiledsData = expectedResult.get(productId);
						for(String k : expectedFiledsData.keySet()){
							if(k.equals("mall_skus") || k.equals("mall_warehouse"))
								continue;
							Assert.assertEquals(filedsData.get(k), expectedFiledsData.get(k),productId + "中,站点" + site + "下的'" + k + "'字段返回值期望不一致。");
						}
					}
				}
			}
		}catch (Exception e) {
			Assert.fail("测试时,发生异常:"+e.getMessage());
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
		 * category : pop_cosmetics mall中show_status=3
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
	 * 仅售卖团购产品检查
	 * @param product_id
	 * @param isSaleable
	 */
	@Test(dataProvider = "data_OnlyDealSaledProductId")
	public void testOnlyDealSaledProductId(String productIds,List<String> sellableSite,List<String> sellabelCategories,String dealSellabeStatus){
		Assert.assertNotNull(productIds,"productIds不能为空");
		Assert.assertNotNull(sellableSite,"sellableSite不能为空");
		Assert.assertNotNull(sellabelCategories,"sellableCategories不能为空");
		Assert.assertNotNull(dealSellabeStatus,"dealSellableStatus不能为空");
		try {
			BasicPortal bp=new BasicPortal();
			String result = bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"接口调用返回异常");
			JSONObject jsonResult = new JSONObject(result);
			Map<String,Map<String,Map<String,String>>> datas = this.parseData(jsonResult);
			//站点下，对应类型没有合适deal时，为空检查
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
						Assert.assertEquals(dealDatas.get("is_on_sale"), dealSellabeStatus,productId + "中,站点" + s + "下,类型" + c + "的可售状态不正确。");
					}
				}else{
					for(String c : sellabelCategories){
						category = categoriesDatas.get(c);
						dealDatas = this.convertJSONObjectToMap(new JSONObject(category));
						Assert.assertEquals(dealDatas.get("is_on_sale"), "0",productId + "中,站点" + s + "下,类型" + c + "的可售状态不正确。");
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("测试时,发生异常:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	@DataProvider(name = "data_OnlyDealSaledProductIdDetailValue")
	public Object[][] dataOnlyDealSaledProductIdDetailValue(){
		/**
		 * 普通deal信息检查:media
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
		 * 普通deal信息检查:product
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
		 * 普通deal信息检查:global
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
		 * 普通deal信息检查:retail_global
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
		 * 普通deal信息检查:combination_global
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
	 * 检查各个站点，各个目录下deal的详细信息
	 * @param productIds
	 * @param sellabelCategories
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_OnlyDealSaledProductIdDetailValue")
	public void testOnlyDealSaledProductIdDetailValue(String productIds,List<String> sellabelCategories,Map<String,String> expectedResult){
		Assert.assertNotNull(productIds,"productIds不能为空");
		Assert.assertNotNull(sellabelCategories,"sellableCategories不能为空");
		Assert.assertNotNull(expectedResult,"expectedResult不能为空");
		try {
			BasicPortal bp=new BasicPortal();
			String result = bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"接口调用返回异常");
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
							Assert.assertEquals(dealData.get(compareFiled), expectedResult.get(compareFiled),productId + "中，站点" + s + "下,类型" + c + "'中deal的'" + compareFiled + "'字段比对错误。");
						}
						//dealOnSale_site字段检查
						Assert.assertEquals(dealData.get("dealOnSale_site"), "[\"\",\"cd\",\"gz\",\"sh\",\"bj\"]",productId + "中，站点" + s + "下,类型" + c + "'中deal的'dealOnSale_site'字段比对错误。");
					}else{
						for(String compareFiled : expectedResult.keySet()){
							Assert.assertEquals(dealData.get(compareFiled), expectedResult.get(compareFiled),productId + "中，站点" + s + "下,类型" + c + "'中deal的'" + compareFiled + "'字段比对错误。");
						}
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("测试时,发生异常:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 商城和deal都售卖的产品填充检查
	 * @param product_id
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_DealAndMallSaledProductId",dataProviderClass=CollectionCenterTestData.class)
	public void testDealAndMallSaledProductId(String productIds, Map<String,Map<String,Map<String,String>>> expectedResult){
		Assert.assertNotNull(productIds,"productIds不能为空");
		Assert.assertNotNull(expectedResult,"expectedResult不能为空");
		try{
			BasicPortal bp=new BasicPortal();
			String result = bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleByProductIds",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"接口调用返回异常");
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
						//检查是否能找到特定的返回关键字段
						Assert.assertNotNull(detailData.get(key) , productId + "中,站点" + s + "下,类型'" + c + "'中没有找到返回字段'" + key + "'。");
						Assert.assertEquals(detailData.get(key), expectedValue , productId + "中,站点" + s + "下,类型'" + c + "'中的'" + key + "'比对错误。");
					}
				}
			}
		}catch (Exception e) {
			Assert.fail("测试时,发生异常:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 测试写收藏表
	 * @param productIds
	 * @param expectedEndTimeAndStatus
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_WriteDataToSaleTable",dataProviderClass=CollectionCenterTestData.class)
	public void testWriteDataToSaleTable(String productId,Map<String,Map<String,String>> expectedEndTimeAndStatus, Map<String,Map<String,Map<String,String>>> expectedResult){
		Assert.assertNotNull(productId,"productIds不能为空");
		Assert.assertNotNull(expectedEndTimeAndStatus,"expectedEndTimeAndStatus不能为空");
		Assert.assertNotNull(expectedResult,"expectedResult不能为空");
		try {
			BasicPortal bp=new BasicPortal();
			String result = bp.exe("JumeiProduct_Write_ProductOnSale","updateByProductId",new JSONArray("[" + productId + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"接口调用返回异常");
			productId = productId.replace("[", "").replace("]", "");
			Assert.assertEquals(result, "true",productId + "更新收藏表不成功。");
			Map<String,Map<String,String>> dbDatas = this.getSaleDataInDb(productId);
			Assert.assertNotNull(dbDatas,productId + "查询数据库错误。");
			Assert.assertTrue(dbDatas.size() > 0,productId + "查询数据库错误。");
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
				//时间比较
				Assert.assertEquals(datas.get("endTime"), expectedEndTimeAndStatus.get(site).get("endTime"),productId + "中,站点" + site + "下,时间比对错误.");
				//状态比对
				Assert.assertEquals(datas.get("status"),expectedEndTimeAndStatus.get(site).get("status"),productId + "中,站点" + site + "下,状态比对错误.");
				//具体值比较
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
										Assert.assertEquals(actualDealInfo.get(field), expectedDealInfo.get(field), productId + "中,站点" + site + "下,类型'" + c + "'下,字段'" + k + "'值比对错误:" + hashId + "下" + field +"比对错误");
									}
								}
							}else{
								Assert.assertEquals(dbCategoryInfo.get(k), expectedCategoryInfo.get(k),productId + "中,站点" + site + "下,类型'" + c + "'下,字段'" + k + "'值比对错误。");
							}
						}
					}else{
						if(!siteData.get(c).equals("[]")){
							mallFlag++;
							fullMallCategory = c;
						}
					}
				}
				//填充mall检查
				expectedMall = expectedResult.get(site).get("mall");
				expectMallCount = expectedMall == null ? 0 : 1;
				Assert.assertEquals(mallFlag, expectMallCount,productId + "中,站点" + site + "下mall信息填充错误。");
				if(expectMallCount == 1){
					//mall具体值检查
					actualMall = this.convertJSONObjectToMap(new JSONObject(siteData.get(fullMallCategory)));
					for(String field : expectedMall.keySet()){
						Assert.assertEquals(actualMall.get(field), expectedMall.get(field),productId + "中,站点" + site + "下,类型'" + fullMallCategory + "'下,字段'" + field + "'值比对错误。");
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("测试时,发生异常:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户中心收藏接口测试
	 * @param productIds
	 * @param site
	 * @param categories
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_GetOnSaleDataByProductIds",dataProviderClass=CollectionCenterTestData.class)
	public void testGetOnSaleDataByProductIds(String productIds, String site, String categories, Map<String,Map<String,String>> expectedResult){
		Assert.assertNotNull(productIds,"productIds不能为空");
		Assert.assertNotNull(site,"site不能为空");
		Assert.assertNotNull(categories,"categories不能为空");
		Assert.assertNotNull(expectedResult,"expectedResult不能为空");
		try{
			BasicPortal bp=new BasicPortal();
			//调用更新接口将收藏数据写入收藏表
			String result = bp.exe("JumeiProduct_Write_ProductOnSale","updateByProductId",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"更新收藏表接口调用返回异常");
			Assert.assertEquals(result, "true",productIds + "更新收藏表不成功。");
			//调用用户中心收藏接口,返回收藏数据
			result = bp.exe("JumeiProduct_Read_ProductOnSale","getOnSaleDataByProductIds",new JSONArray("[" + productIds + "," + site + "," + categories + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"用户中心收藏接口调用返回异常");
			Map<String,String> returnDatas = this.convertJSONObjectToMap(new JSONObject(result));
			Assert.assertEquals(returnDatas.size(), expectedResult.size(),productIds + "返回结果条数与期望条数不一致。");
			Map<String,String> detailInfo = null;
			Map<String,String> expectedInfo = null;
			for(String proId : expectedResult.keySet()){
				detailInfo = this.convertJSONObjectToMap(new JSONObject(returnDatas.get(proId)));
				expectedInfo = expectedResult.get(proId);
				for(String key : expectedInfo.keySet()){
					Assert.assertEquals(detailInfo.get(key), expectedInfo.get(key),proId + "下,字段'" + key + "'检查错误。");
				}
			}
		} catch (Exception e) {
			Assert.fail("测试时,发生异常:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 购物车下方推荐
	 * @param productIds
	 * @param site
	 * @param expectedResult
	 */
	@Test(dataProvider = "data_GetRecommendsForShop",dataProviderClass=CollectionCenterTestData.class)
	public void testGetRecommendsForShop(String productIds, String site, Map<String,Map<String,String>> expectedResult){
		Assert.assertNotNull(productIds,"productIds不能为空");
		Assert.assertNotNull(site,"site不能为空");
		Assert.assertNotNull(expectedResult,"expectedResult不能为空");
		try{
			BasicPortal bp=new BasicPortal();
			//调用更新接口将收藏数据写入收藏表
			String result = bp.exe("JumeiProduct_Write_ProductOnSale","updateByProductId",new JSONArray("[" + productIds + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"更新收藏表接口调用返回异常");
			Assert.assertEquals(result, "true",productIds + "更新收藏表不成功。");
			//调用购物车收藏接口,返回收藏数据
			result = bp.exe("JumeiProduct_Read_Recommend","getRecommendsForShop",new JSONArray("[" + productIds + "," + site + "]"),"192.168.16.140","3201");
			Assert.assertNotNull(result,"购物车收藏接口调用返回异常");
			Map<String,String> returnDatas = this.convertJSONObjectToMap(new JSONObject(result));
			Assert.assertEquals(returnDatas.size(), expectedResult.size(),productIds + "返回结果条数与期望条数不一致。");
			for(String proId : expectedResult.keySet()){
				Map<String,String> detailInfo = this.convertJSONObjectToMap(new JSONObject(returnDatas.get(proId)));
				Map<String,String> expectedInfo = expectedResult.get(proId);
				for(String key : expectedInfo.keySet()){
					Assert.assertEquals(detailInfo.get(key), expectedInfo.get(key),proId + "下,字段'" + key + "'检查错误。");
				}
			}
		}catch (Exception e) {
			Assert.fail("测试时,发生异常:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查product_id下的数据结构
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
			//检查下面对应的站点信息是不是等于5
			Assert.assertEquals(productResult.length(), 5,productId + "产品下对应站点不等5个。");
			//站点值检查
			ite = productResult.keys();
			while(ite.hasNext()){
				site = ite.next();
				if(!sites.contains(site)){
					Assert.fail(productId + "中找到非法站点:"+site);
				}
			}
			//各个站点下category检查
			for(String s : sites){
				siteResult = productResult.getJSONObject(s);
				//每个站点下的category检查
				ite = siteResult.keys();
				while(ite.hasNext()){
					category = ite.next();
					if(!categories.contains(category)){
						Assert.fail(productId + "中,站点" + s + "下找到非法category:" + category);
					}
				}
			}
		}
	}
	
	/**
	 * 解析返回的json,转换成java对象
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
	 * 检查product_id下对应站点下所有category都是空
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
						Assert.fail(productId + "中，站点" + site + "下的" + category + "类型不为空。");
					}
				}
			}
		}
		
	}
	
	/**
	 * 仅商城售卖时，填充数据检查
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
						Assert.assertEquals(categoryObject.getString("is_on_sale"), "1",productId + "中,站点" + site + "下的类型" + category + "没有为可售.");
					}
				}else{
					for(String category : categories){
						categoryValue = categoryData.get(category);
						categoryObject = new JSONObject(categoryValue);
						Assert.assertEquals(categoryObject.getString("is_on_sale"), "0",productId + "中,站点" + site + "下的类型" + category + "没有为不可售.");
					}
				}
			}
		}
	}
	
	/**
	 * 将json对象转换成map对象
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
	 * 根据产品id从收藏表中查询数据
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

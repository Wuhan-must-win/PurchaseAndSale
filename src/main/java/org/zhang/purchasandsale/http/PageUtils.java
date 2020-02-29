package org.zhang.purchasandsale.http;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 
 * @category 分页工具类(转换jpa，gdb，mybatisplus分页数据到reqpage)
 * @author G.fj
 * @since 2019年12月23日 下午7:48:18
 *
 */
public class PageUtils {
	/**
	 * @category 转换Jpa的分页参数
	 * @param page
	 * @return
	 */
	public static Page toPage(org.springframework.data.domain.Page jpage) {
		Page page = new Page();
		page.setPageNumber(jpage.getNumber());
		page.setPageSize(jpage.getSize());
		page.setPageCount(jpage.getTotalPages());
		page.setRowCount(jpage.getTotalElements());
		page.setRows(jpage.getContent());
		return page;
	}

	/**
	 * @category 转换MybatisPlus的分页参数
	 * @param ipage
	 * @return
	 */
	public static Page toPage(IPage ipage) {
		Page page = new Page();
		page.setPageNumber((int) ipage.getCurrent());
		page.setPageSize((int) ipage.getSize());
		page.setRowCount(ipage.getTotal());
		page.setPageCount((int) ipage.getPages());
		return page;
	}

	/**
	 * @category 转换Gdb的分页参数
	 * @param page
	 * @return
	 */
//	public static Page toPage(GPage gpage) {
//		Page page = new Page();
//		page.setPageNumber(gpage.getPageNumber());
//		page.setPageSize(gpage.getPageSize());
//		page.setPageCount(gpage.getPageCount());
//		page.setRowCount(gpage.getRowCount());
//		return page;
//	}
}

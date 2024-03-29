package com.chesterccw.excelreader.util;

import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Enumeration;

public class TableTool {

	private static final Color BASE_COLOR = JBColor.WHITE;
	private static final Color EVEN_COLOR = new JBColor(new Color(243, 249, 255), new Color(243, 249, 255));
	private static final Color GREW_COLOR = Gray._245;
	private static final Color DEFAULT_COLOR = Gray._60;
	private static final Color DARK_COLOR = new JBColor(Gray._36, Gray._36);
	private static final Color DARK_FONT_COLOR = new JBColor(Gray._187, Gray._187);
	private static final Color SELECTED_BACKGROUND_COLOR = new JBColor(new Color(40, 145, 225), new Color(40, 145, 225));

	/**
	 * 设置 JTable 样式
	 * @param table JTable
	 */
	public void setTableStyle(JBTable table) {
		// set bg for selected item
		table.setSelectionBackground(new JBColor(new Color(48, 106, 190), new Color(48, 106, 190)));
		table.setSelectionForeground(Gray._255);
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(20);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		table.getTableHeader().setDefaultRenderer(headerRenderer);

		MyTableCellRenderer r = new MyTableCellRenderer();
		r.setHorizontalAlignment(SwingConstants.LEFT);
		table.setDefaultRenderer(Object.class, r);
		table.getTableHeader().setFont(MyFont.Bold);
		table.setFont(MyFont.Common);
		setDefaultColumnColor(table);
	}

	private static void unfoldAllCol(JTable table) {
		JTableHeader header = table.getTableHeader();
		int rowCount = table.getRowCount();
		Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) table.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(table, column.getIdentifier()
							, false, false, -1, col).getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int tempWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table,
						table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				width = Math.max(width, tempWidth);
			}
			header.setResizingColumn(column);
			column.setWidth(width + table.getIntercellSpacing().width);
		}
	}

	public static void unfoldCol(JTable table, int col) {
		if (table == null) {
			return;
		}

		if (col < 0) {
			unfoldAllCol(table);
			return;
		}

		JTableHeader header = table.getTableHeader();
		TableColumn column = table.getColumn(table.getColumnName(col));
		int rowCount = table.getRowCount();
		int width = (int) table.getTableHeader().getDefaultRenderer()
				.getTableCellRendererComponent(table, column.getIdentifier()
						, false, false,
						-1, col).getPreferredSize().getWidth();
		for (int row = 0; row < rowCount; row++) {
			int tempWidth = (int) table.getCellRenderer(row, col).
					getTableCellRendererComponent(table,
							table.getValueAt(row, col), false, false,
							row, col).getPreferredSize().getWidth();
			width = Math.max(width, tempWidth);
		}
		header.setResizingColumn(column);
		column.setWidth(width + table.getIntercellSpacing().width + 20);
	}

	/**
	 * set JScrollPane style
	 * @param scrollPane JScrollPane
	 */
	public void setJspStyle(JScrollPane scrollPane) {
		if(scrollPane == null){
			return;
		}
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getVerticalScrollBar().setOpaque(false);
		scrollPane.getHorizontalScrollBar().setOpaque(false);
	}

	private static void setDefaultColumnColor(JTable table) {
		try {
			MyTableCellRenderer tcr = new MyTableCellRenderer();
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class MyTableCellRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if(!UIUtil.isUnderDarcula()){
				if(isSelected){
					setBackground(SELECTED_BACKGROUND_COLOR);
					setForeground(JBColor.WHITE);
				} else {
					if ((row+1) % 6 == 0) {
						setBackground(EVEN_COLOR);
					} else if ((row+1) % 3 == 0) {
						setBackground(GREW_COLOR);
					} else {
						setBackground(BASE_COLOR);
					}
					setForeground(DEFAULT_COLOR);
				}
			} else {
				if(isSelected){
					setBackground(SELECTED_BACKGROUND_COLOR);
					setForeground(JBColor.WHITE);
				} else {
					if ((row+1) % 3 == 0) {
						setBackground(DARK_COLOR);
					} else {
						setBackground(BASE_COLOR);
					}
					setForeground(DARK_FONT_COLOR);
				}
			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

		@Override
		public void setHorizontalAlignment(int alignment) {
			super.setHorizontalAlignment(alignment);
		}
	}

}

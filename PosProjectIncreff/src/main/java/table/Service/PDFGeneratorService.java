package table.Service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import table.Dao.PDFGeneratorDao;
import table.Pojo.OrderItemPojo;
import table.Pojo.OrderPojo;

@Service
public class PDFGeneratorService {

	@Autowired
	private PDFGeneratorDao dao;

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.GRAY);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.BLACK);

		cell.setPhrase(new Phrase("Brand", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Quantity", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Price", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Subtotal", font));
		table.addCell(cell);

	}

	@Transactional(rollbackOn = ApiException.class)
	public void export(HttpServletResponse response, List<OrderItemPojo> list2) throws IOException, DocumentException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(18);

		Paragraph paragraph = new Paragraph("Invoice", fontTitle);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(12);

		OrderPojo op = getOrder(list2.get(0).getOrderId());
		String[] list1 = op.getdT().split(" ");

		Paragraph paragraph4 = new Paragraph("Order Id: " + op.getId() + "", font);
		paragraph4.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraph4);

		Paragraph paragraph1 = new Paragraph("Invoice Date: " + list1[0]
				+ "                                                                                             Time: "
				+ list1[1].substring(0, 5) + "", font);
		paragraph1.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraph1);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 3.5f, 3.5f, 1.5f, 1.5f, 1.5f });
		table.setSpacingBefore(10);

		writeTableHeader(table);

		double val = 0;
		for (OrderItemPojo o : list2) {
			List<String> l = dao.get(o);
			table.addCell(l.get(0));
			table.addCell(l.get(1));
			table.addCell(String.valueOf(o.getQuantity()));
			table.addCell(String.valueOf(o.getMrp()));
			table.addCell(String.valueOf(o.getMrp() * o.getQuantity()));
			val += o.getMrp() * o.getQuantity();

		}

		document.add(table);
		Paragraph paragraphl = new Paragraph("Total: " + val, fontTitle);
		paragraphl.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(paragraphl);

		document.close();
	}

	@Transactional
	public List<OrderItemPojo> getList() {
		return dao.getList();
	}

	@Transactional
	public List<OrderItemPojo> getSpecList(int id) {
		return dao.getSpecList(id);
	}

	@Transactional
	public OrderPojo getOrder(int orderId) {
		return dao.getOrder(orderId);
	}
}
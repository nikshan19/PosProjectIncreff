package table.Service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import table.Model.OrderItemData;
import table.Pojo.OrderItemPojo;

@Service
public class PDFGeneratorService {

	@Autowired
	private PDFGeneratorDao dao;

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Brand", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Quantity", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Price", font));
		table.addCell(cell);

	}

	@Transactional(rollbackOn = ApiException.class)
	public void export(HttpServletResponse response, List<OrderItemPojo> list2) throws IOException, DocumentException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(18);

		Paragraph paragraph = new Paragraph("Increff", fontTitle);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 3.5f, 3.5f, 1.5f, 1.5f });
		table.setSpacingBefore(10);

		writeTableHeader(table);

		double val = 0;
		for (OrderItemPojo o : list2) {
			List<String> l = dao.get(o);
			table.addCell(l.get(0));
			table.addCell(l.get(1));
			table.addCell(String.valueOf(o.getQuantity()));
			table.addCell(String.valueOf(o.getMrp()));
			val += o.getMrp() * o.getQuantity();

		}

		document.add(table);
		Paragraph paragraphl = new Paragraph("Total: " + val, fontTitle);
		paragraphl.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraphl);

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(12);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		Paragraph paragraph1 = new Paragraph("Date: " + dtf.format(now) + "\n\n", font);
		paragraphl.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(paragraph1);

		Paragraph paragraph2 = new Paragraph("Signature", font);
		paragraphl.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(paragraph2);

		System.out.println("size: " + list2.size());
		document.close();
	}

	@Transactional
	public List<OrderItemPojo> getList() {
		return dao.getList();
	}
}
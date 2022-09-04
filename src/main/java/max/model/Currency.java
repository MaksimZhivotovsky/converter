package max.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import max.util.DateUtils;

@Data
@Entity
@Table(name = "currencies_tbl")
public class Currency implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "curr_id")
	private Integer id;

	@Column(name = "curr_ident")
	private String ident;

	@Column(name = "curr_numcode")
	private BigDecimal numCode;

	@Column(name = "curr_charcode")
	private String charCode;

	@Column(name = "curr_nominal")
	private BigDecimal nominal;

	@Column(name = "curr_name")
	private String name;

	@Column(name = "curr_value")
	private BigDecimal value;

	@Column(name = "curr_date")
	private Date date;

	public static Currency getBase(){
		Currency rub = new Currency();
		rub.setNumCode(new BigDecimal(643));
		rub.setCharCode("RUB");
		rub.setNominal(new BigDecimal(1));
		rub.setName("Российский рубль");
		rub.setValue(new BigDecimal(1));
		rub.setDate(DateUtils.getCurrentDateWOTime());

		return rub;
	}

}

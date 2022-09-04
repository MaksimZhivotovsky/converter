package max.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "convert_history_tbl")
public class ConvertHistory {

	@Id
	@Column(name = "conv_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "conv_from")
	private String from;
	@Column(name = "conv_to")
	private String to;
	@Column(name = "conv_value_from")
	private BigDecimal value_from;
	@Column(name = "conv_value_to")
	private BigDecimal value_to;
	@Column(name = "conv_date")
	private Date date;
}

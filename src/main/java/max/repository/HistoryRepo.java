package max.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import max.model.ConvertHistory;

public interface HistoryRepo extends JpaRepository<ConvertHistory, Long> {
	@Override
	List<ConvertHistory> findAll();

	@Override
	List<ConvertHistory> findAll(Sort sort);

	@Query( value = "SELECT * FROM convert_history_tbl c WHERE c.conv_date = :date ORDER BY c.conv_id DESC LIMIT :limit" , nativeQuery = true )
	List<ConvertHistory> getConvertHistories( @Param("date") Date date, @Param("limit") int limit);

	@Query( value = "SELECT DISTINCT conv_from FROM convert_history_tbl ORDER BY conv_from ASC", nativeQuery = true )
	List<String> getFromDistinct();

	@Query( value = "SELECT DISTINCT conv_to FROM convert_history_tbl ORDER BY conv_to ASC", nativeQuery = true )
	List<String> getToDistinct();

	List<ConvertHistory> findAllByDateAndFromAndToOrderById(Date date,String from,String to);

	@Override
	List<ConvertHistory> findAllById(Iterable<Long> iterable);

	@Override
	<S extends ConvertHistory> List<S> saveAll(Iterable<S> entities);

	@Override
	void flush();

	@Override
	<S extends ConvertHistory> S saveAndFlush(S entity);

	@Override
	void deleteInBatch(Iterable<ConvertHistory> entities);

	@Override
	void deleteAllInBatch();

	@Override
	ConvertHistory getOne(Long l);

	@Override
	<S extends ConvertHistory> List<S> findAll(Example<S> example);

	@Override
	<S extends ConvertHistory> List<S> findAll(Example<S> example, Sort sort);

	@Override
	Page<ConvertHistory> findAll(Pageable pageable);

	@Override
	<S extends ConvertHistory> S save(S entity);

	@Override
	Optional<ConvertHistory> findById(Long l);

	@Override
	boolean existsById(Long l);

	@Override
	long count();

	@Override
	void deleteById(Long l);

	@Override
	void delete(ConvertHistory entity);

	@Override
	void deleteAll(Iterable<? extends ConvertHistory> entities);

	@Override
	void deleteAll();

	@Override
	<S extends ConvertHistory> Optional<S> findOne(Example<S> example);

	@Override
	<S extends ConvertHistory> Page<S> findAll(Example<S> example, Pageable pageable);

	@Override
	<S extends ConvertHistory> long count(Example<S> example);

	@Override
	<S extends ConvertHistory> boolean exists(Example<S> example);

}

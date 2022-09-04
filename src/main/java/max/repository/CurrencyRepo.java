package max.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import max.model.Currency;

public interface CurrencyRepo extends JpaRepository<Currency, Integer> {

	@Override
	List<Currency> findAll();

	@Override
	List<Currency> findAll(Sort sort);

	@Override
	List<Currency> findAllById(Iterable<Integer> iterable);

	List<Currency> findAllByDate(Date date);

	Optional<Currency> getFirstByCharCodeOrderByDateDesc(String from);

	@Override
	<S extends Currency> List<S> saveAll(Iterable<S> entities);

	@Override
	void flush();

	@Override
	<S extends Currency> S saveAndFlush(S entity);

	@Override
	void deleteInBatch(Iterable<Currency> entities);

	@Override
	void deleteAllInBatch();

	@Override
	Currency getOne(Integer integer);

	@Override
	<S extends Currency> List<S> findAll(Example<S> example);

	@Override
	<S extends Currency> List<S> findAll(Example<S> example, Sort sort);

	@Override
	<S extends Currency> boolean exists(Example<S> example);

	<S extends Currency> boolean existsByDate(Date date);
}

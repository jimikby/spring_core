package com.epam.theatre.config;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.epam.theatre.domain.Auditorium;
import com.epam.theatre.logic.DiscountStrategy;
import com.google.common.collect.Lists;

@Configuration
@ComponentScan(basePackages = "com.epam.theatre")
@EnableAspectJAutoProxy
@PropertySource({ "classpath:auditoriums.properties", "classpath:discounts.properties" })

public class AppConfigCommon {

	@Autowired
	@Qualifier("everyTenStrategy")
	private DiscountStrategy everyTenStrategy;

	@Autowired
	@Qualifier("birthdayStrategy")
	private DiscountStrategy birthdayStrategy;

	@Bean(name = "discountStrategies")
	public List<DiscountStrategy> getDiscountStrategies() {
		return Lists.newArrayList(everyTenStrategy, birthdayStrategy);

	}

	@Autowired
	private DataSource dataSource;

	@Bean
	public DataSource dataSource() {

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase dataSource = builder.setType(EmbeddedDatabaseType.DERBY)
				.addScript("create_db.sql")
				.addScript("fill_db.sql")
				.build();

		return dataSource;
	}

	@Bean
	public JdbcTemplate getjdbcTemplate() throws NamingException {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	@Qualifier("auditoriumBig")
	private Auditorium auditoriumBig;

	@Autowired
	@Qualifier("auditoriumMid")
	private Auditorium auditoriumMid;

	@Autowired
	@Qualifier("auditoriumSmall")
	private Auditorium auditoriumSmall;

	@Bean
	public Auditorium auditoriumBig() {
		return new Auditorium();
	}

	@Bean
	public Auditorium auditoriumMid() {
		return new Auditorium();
	}

	@Bean
	public Auditorium auditoriumSmall() {
		return new Auditorium();
	}

	// big
	@Value("${big.id}")
	private Long auditoriumBigId;

	@Value("${big.name}")
	private String auditoriumBigName;

	@Value("${big.numberOfSeats}")
	private Long auditoriumBigSeats;

	@Value("${big.vipSeats}")
	private String auditoriumBigVipSeats;

	// mid
	@Value("${mid.id}")
	private Long auditoriumMidId;

	@Value("${mid.name}")
	private String auditoriumMidName;

	@Value("${mid.numberOfSeats}")
	private Long auditoriumMidSeats;

	@Value("${mid.vipSeats}")
	private String auditoriumMidVipSeats;

	// small
	@Value("${small.id}")
	private Long auditoriumSmallId;

	@Value("${small.name}")
	private String auditoriumSmallName;

	@Value("${small.numberOfSeats}")
	private Long auditoriumSmallSeats;

	@Value("${small.vipSeats}")
	private String auditoriumSmallVipSeats;

	@Bean
	public List<Auditorium> getAuditoriumList() {

		auditoriumBig.setAuditoriumId(auditoriumBigId);
		auditoriumBig.setName(auditoriumBigName);
		auditoriumBig.setNumberOfSeats(auditoriumBigSeats);
		auditoriumBig.setVipSeats(vipSeatsExplodeFromString(auditoriumBigVipSeats));

		auditoriumMid.setAuditoriumId(auditoriumMidId);
		auditoriumMid.setName(auditoriumMidName);
		auditoriumMid.setNumberOfSeats(auditoriumMidSeats);
		auditoriumMid.setVipSeats(vipSeatsExplodeFromString(auditoriumMidVipSeats));

		auditoriumSmall.setAuditoriumId(auditoriumSmallId);
		auditoriumSmall.setName(auditoriumSmallName);
		auditoriumSmall.setNumberOfSeats(auditoriumSmallSeats);
		auditoriumSmall.setVipSeats(vipSeatsExplodeFromString(auditoriumSmallVipSeats));

		return Lists.newArrayList(auditoriumBig, auditoriumMid, auditoriumSmall);

	}

	private Set<Long> vipSeatsExplodeFromString(String vipSeats) {

		return Pattern.compile(",").splitAsStream(vipSeats).map(Long::parseLong).collect(Collectors.toSet());

	}

}

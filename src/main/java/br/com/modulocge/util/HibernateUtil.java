package br.com.modulocge.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	// O objeto que guarda a fabrica das sessões, irá receber a fabrica
	// criada pela criarFabricaDeSessoes, externamente utilizar get.
	// criarFabricaDeSessoes();
	private static SessionFactory sessionFactory = criarFabricaDeSessoes();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	//Método utilizado no jasperReport
	public static Connection getConexao(){
		Session sessao =  sessionFactory.openSession();
		
		Connection conexao = 
		sessao.doReturningWork(new ReturningWork<Connection>() {
			@Override
			public Connection execute(Connection conn) throws SQLException {
				return conn;
			}
		});
		
		return conexao;
	}
	
	private static SessionFactory criarFabricaDeSessoes() {
		try {
			// Carrega as configurações definida no arquivo hibernate.cfg.xml
			Configuration configuracao = new Configuration().configure();

			// Registrar o serviço - Recebe StandarServiceRegistryBuilder, em
			// seguida passa o método apllySettings
			// passando a configuração para capturar as propriedades e ao final
			// um build().
			ServiceRegistry registro = new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties())
					.build();

			// Cria a fabrica de sessões - Recebe a configuração com
			// buildSessionFactory passando o registro do serviço
			SessionFactory fabrica = configuracao.buildSessionFactory(registro);

			// Retornar a fabrica
			return fabrica;
		} catch (Throwable erro) {
			System.err.println("A criação da fabrica de sessões não pode ser criada." + erro);
			// É necessário inserir o throw para emitir o erro da sessão
			throw new ExceptionInInitializerError(erro);
		}
	}
}

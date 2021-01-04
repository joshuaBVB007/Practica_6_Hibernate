package practica6;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sesionFact = null;
		Session session = null;
		Transaction transac=null;
		Scanner entrada=new Scanner(System.in);
		while(true) {
			System.out.println("Ingresa la operacion"
					+ "\n1.Listado Departamentos"
					+ "\n2.Listado Empleados"
					+ "\n3.Ver un departamento con ID"
					+ "\n4.Ver empleado por ID"
					+ "\n5.Consultar datos de empleado por nombre"
					+ "\n6.Consultar datos de empleado por dept_no"
					+ "\n7.Consultar datos de empleado por dept_no sin HQL"
					+ "\n8.Min,Max,Medio de todos los empleados"
					+ "\n9.Lista Empleados con sus departamentos"
					+ "\n10.Consulta Datos Departamento:Media salarial,nombre,Nª empleados"
					+ "\n11.Ingresar un Departamento"
					+ "\n12.Ingresar un Empleado"
					+ "\n13.Subir el salario de empleados de un departamento"
					+ "\n14.Subir el salario de un empleado cambiando el departamento"
					+ "\n0.Finalizar");
			int dato=entrada.nextInt();
			sesionFact=HibernateUtil.getSessionFactory();
			session=sesionFact.openSession();
			transac=session.beginTransaction();
			if(dato==1) {//V
				Query consulta=session.createQuery("from Depart");
				List <Depart> lista=consulta.getResultList();
				System.out.println(lista.size());
				for (Depart depart : lista) {
					System.out.println(depart.getDnombre());
				}
			}else if(dato==2) {//V
				Query consulta=session.createQuery("from Emple");
				List <Emple> lista2=consulta.getResultList();
				for (Emple emple : lista2) {
					System.out.println(emple.getApellido());
				}
			}else if(dato==3){//V
				System.out.println("Ingresa el ID del departamento");
				int ID=entrada.nextInt();
				Depart foo = (session.get(Depart.class, ID));
				System.out.println(foo.getDnombre());
			}else if(dato==4){//V
				System.out.println("Ingresa el ID del empleado");
				int ID_emp=entrada.nextInt();
				Emple foo = (session.load(Emple.class, ID_emp));
				System.out.println(foo.getApellido());
			}else if(dato==5) {//V
				System.out.println("Introduce un nombre de empleado");
				String Nombre=entrada.next();
				Query consulta=session.createQuery("from Emple where apellido="+"'"+Nombre+"'"+"");
				List <Emple> lista2=consulta.getResultList();
				for (Emple emple : lista2) {
					System.out.println("Apellido: "+emple.getApellido());
					System.out.println("Oficio: "+emple.getOficio());
					System.out.println("Comision: "+emple.getComision());
					System.out.println("Fecha Alta: "+emple.getFechaAlt());
				}
			}else if(dato==6) {//V
				System.out.println("Introduce un numero de departamento");
				String dep=entrada.next();
				Query consulta=session.createQuery("from Emple where dept_no="+"'"+dep+"'"+"");
				List <Emple> lista2=consulta.getResultList();
				System.out.println("Lista empleados del departamento "+dep);
				for (Emple emple : lista2) {
					System.out.println("Apellido: "+emple.getApellido());
					System.out.println("Oficio: "+emple.getOficio());
					System.out.println("Comision: "+emple.getComision());
					System.out.println("Fecha Alta: "+emple.getFechaAlt());
					System.out.println("---------------------------------");
				}
			}else if(dato==7) {//V
				System.out.println("Ingresa el ID del departamento");
				int ID=entrada.nextInt();
				Depart foo = (session.get(Depart.class, ID));
				System.out.println("Nombre Departamento :"+foo.getDnombre());
				System.out.println("Lista Empleados");
				Set <Emple> lista2=foo.getEmples();
				for (Emple emple : lista2) {
					System.out.println(emple.getApellido());
				}
			}else if(dato==8) {//V
				Query media=session.createQuery(
						"select round(avg(cat.salario)) from Emple cat");
				List <Double> lista2=media.getResultList();
				for (Double string : lista2) {
					System.out.println("MEDIA SALARIAL: "+string);
				}
				Query minima=session.createQuery(
						"select round(min(cat.salario)) from Emple cat");
				List <Integer> lista3=minima.getResultList();
				for (Integer string : lista3) {
					System.out.println("MIN SALARIO: "+string);
				}
				Query maxima=session.createQuery(
						"select round(max(cat.salario)) from Emple cat");
				List <Integer> lista4=maxima.getResultList();
				for (Integer string : lista4) {
					System.out.println("MAX SALARIO: "+string);
				}
			}else if(dato==9) {//V
				Query consulta=session.createQuery("from Emple");
				List <Emple> lista2=consulta.getResultList();
				for (Emple emple : lista2) {
					System.out.println("Nombre Empleado: "+emple.getApellido()+" --- "+emple.getDepart().getDnombre());
				}
			}else if(dato==10) {//V
				Query query=session.createQuery("select kitten.depart.deptNo,cat.dnombre,count(kitten.apellido),avg(kitten.salario) from Depart as cat\r\n"
						+ "				inner join cat.emples as kitten\r\n"
						+ "				 group by cat.deptNo");
				
				List <Object[]> lista2=query.list();
				for (Object[] objeto : lista2) {
					System.out.println("Nª departamento: "+objeto[0]+"\nNombre Departamento: "+objeto[1]+"\nNumero Empleados del Departamento: "+objeto[2]+"\nMedia Salarial del Departamento: "+objeto[3]);
					System.out.println("---------------");
				}
				
			}else if(dato==11) {
				System.out.println("Introduce el numero del departamento ej:80,90");
				int numero_dep=entrada.nextInt();
				System.out.println("Introduce el nombre del departamento ej:Hallazgos,Inspeccion");
				String nom=entrada.next();
				System.out.println("Introduce la localidad del departamento ej:SEVILLA,MADRID");
				String loc=entrada.next();
				Depart nuevo_dep=new Depart();
				nuevo_dep.setDeptNo(numero_dep);
				nuevo_dep.setDnombre(nom.toUpperCase());
				nuevo_dep.setLoc(loc.toUpperCase());
				session.save(nuevo_dep);
				transac.commit();
			}else if(dato==12) {//falata validar que ya exista el empleado
				Emple emple=new Emple();
				while(true) {//VALIDA LOS ID DEL EMPLEADO
					System.out.println("Introduce el ID_empleado? ejemplo 7369");
					int emp_no=entrada.nextInt();
					Emple foo1 = (session.get(Emple.class, emp_no));
					if(foo1!=null) {
						System.out.println("Hay un empleado con ese ID");
					}else {
						System.out.println("ID disponible,Adelante!");
						emple.setEmpNo(emp_no);
						break;
					}
				}
				while(true) {//VALIDA LA EXISTENCIA DEL DEPARTAMENTO
					System.out.println("A que departamento va?");
					int dep=entrada.nextInt();
					Depart foo = (session.get(Depart.class, dep));
					if(foo==null) {
						System.out.println("Este departamento no existe");
					}else {
						System.out.println("Asignando al departamento "+foo.getDnombre());
						emple.setDepart(foo);
						break;
					}
				}
				System.out.println("Ingresa el apellido del empleado");
				String apellido=entrada.next();
				emple.setApellido(apellido.toUpperCase());
				
				System.out.println("Ingresa el oficio del empleado");
				String oficio=entrada.next();
				emple.setOficio(oficio.toUpperCase());
				
				//Por Defecto no les asigno jefe
				emple.setDir(null);
				
				//por defecto les asigno una fecha
				Date e=new Date(1900,11,1);
				emple.setFechaAlt(e);
				
				System.out.println("Ingresa el salario");
				int salario=entrada.nextInt();
				emple.setSalario(salario);
				
				//por defecto no les doy comision
				emple.setComision(null);
				
				session.save(emple);
				transac.commit();
				session.close();
			}else if(dato==13) {
				System.out.println("A que departamento se le hace el incremento");
				int dep=entrada.nextInt();
				String dep_string=String.valueOf(dep);
				System.out.println("incremento?");
				int incr=entrada.nextInt();
				String incr_string=String.valueOf(incr);
				session.createQuery("update Emple c set salario = (salario+"+incr_string+") where dept_no = "+dep_string+"").executeUpdate();
				transac.commit();
				session.close();
			}else if(dato==14) {
				System.out.println("Ingresa ID empleado");
				int ID_empleado=entrada.nextInt();
				String dep_string=String.valueOf(ID_empleado);
				
				System.out.println("incremento?");
				int incr=entrada.nextInt();
				String incr_string=String.valueOf(incr);

				System.out.println("Dept_no al que va?");
				int va=entrada.nextInt();
				
				session.createQuery("update Emple c set salario = (salario+"+incr_string+") where emp_no = "+dep_string+"").executeUpdate();
				session.createQuery("update Emple c set dept_no = "+va+" where emp_no = "+dep_string+"").executeUpdate();
				transac.commit();
				session.close();
			}else if(dato==0) {
				sesionFact.close();
				break;
			}
		}
		
	}
}

package com.hibernate.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.hibernate.model.Product;

public class Main {

	private static EntityManagerFactory factory = HibernateOGMUtil.getEntityManagerFactory();;

	public static void main(String[] args) {

		String id;
		int id1 = 0;
		String name;
		String description;
		double price;
		int quantity;
		int opt = 0;
		Scanner sc = new Scanner(System.in);
		Product prod;
		EntityManager em = factory.createEntityManager();

		Set<Product> prods = new HashSet<>();
		do {

			System.out.println("1.Enter data into Database");
			System.out.println("2.View Data");
			System.out.println("3.Update Existing Data");
			System.out.println("4.Delete Data from Database");
			System.out.println("5.Exit from Application");
			System.out.println("Enter Option Value: ");
			opt = sc.nextInt();
			switch (opt) {
			case 1:
				boolean isValid = true;
				do {
					System.out.println("Enter product ID:");
					id = sc.next();
					System.out.println("Enter product name:");
					name = sc.next();
					System.out.print("Enter product description:");
					description = sc.next();
					System.out.println("Enter product price:");
					price = sc.nextDouble();
					System.out.print("Enter product quantity:");
					quantity = sc.nextInt();
					prod = new Product(id, name, description, price, quantity);
					if(id == "" || name == "" || description == "" || price == 0 || quantity == 0) {
						System.out.println("Please enter Correct data!");
						System.err.println("Data incomplete");
					}else {
						isValid = false;
					}
				} while(isValid);
				
				prods.add(prod);
				
				em.getTransaction().begin();
				em.merge(prod);
				em.getTransaction().commit();
				System.out.println("Data Added Successfully!!");
				break;
			case 2:
					List<Product> products = em.createQuery("select s from Product s", Product.class).getResultList();
					 for(Product product : products) {
				            System.out.println("ID: " + product.getId()+ " Product Name:" + product.getName() + " Price: " + product.getPrice() + " Quantity: " + product.getQuantity());
				        }	
				break;
			case 3:
				System.out.print("Enter Id to Update: ");
				id = sc.next();
//				prod = em.createQuery("from Product s where s.id = :id", Product.class)
//						.setParameter("id", id)
//						.getResultList()
//						.get(0);
				System.out.print("Enter product name:");
				name = sc.next();
				System.out.print("Enter product description:");
				description = sc.next();
				System.out.println("Enter product price:");
				price = sc.nextDouble();
				System.out.print("Enter product quantity:");
				quantity = sc.nextInt();
				prod = new Product(id, name, description, price, quantity);
				
				prods.add(prod);
				
				em.getTransaction().begin();
				em.merge(prod);
				em.getTransaction().commit();
				System.out.println("Data Updated");
				break;
			case 4:
				System.out.print("Enter Id to Delete: ");
				id = sc.next();
				em.getTransaction().begin();
				em.remove(em.find(Product.class, id));
				em.getTransaction().commit();
				System.out.println("Data Deleted!!");
				break;
			case 5:
				System.out.println("Thank you for Using the Application!!");
				break;
			}
		} while (opt != 5);
		sc.close();

	}

}

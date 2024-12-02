package com.github.jeanbezerra.ecommerce.web.portal.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.jeanbezerra.ecommerce.web.portal.dto.ProdutoDTO;
import com.github.jeanbezerra.ecommerce.web.portal.entity.Produto;
import com.github.jeanbezerra.ecommerce.web.portal.helper.HibernateEntityManagerHelper;

import jakarta.ejb.Stateless;
import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Stateless
public class ProdutoDAO implements Serializable{
	
	private static final long serialVersionUID = 8954265390033765119L;

	public ProdutoDAO() {
		// TODO Auto-generated constructor stub
	}

	public List<ProdutoDTO> buscarProdutosComCategoria() {
        EntityManager entityManager = null;
        try {
            entityManager = HibernateEntityManagerHelper.getEntityManager();
            String sql = """
                    SELECT 
                        p.produto_id as produto_id,
                        p.nome as produto_nome,
                        p.descricao as produto_desc,
                        p.preco as produto_preco,
                        p.estoque as produto_estoque,
                        p.imagem_url as produto_image,
                        c.nome as produto_categoria
                    FROM produtos p
                    LEFT JOIN categorias c ON c.categoria_id = p.categoria_id
                    ORDER BY p.nome
                    """;

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> results = query.getResultList();

            List<ProdutoDTO> produtos = new ArrayList<>();
            for (Object[] row : results) {
                produtos.add(new ProdutoDTO(
                    ((Number) row[0]).longValue(),  // produto_id
                    (String) row[1],               // produto_nome
                    (String) row[2],               // produto_desc
                    ((Number) row[3]).doubleValue(), // produto_preco
                    ((Number) row[4]).intValue(),  // produto_estoque
                    (String) row[5],               // produto_image
                    (String) row[6]                // produto_categoria
                ));
            }

            return produtos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (entityManager != null) {
                HibernateEntityManagerHelper.closeEntityManager();
            }
        }
    }
	
	public ProdutoDTO buscarProdutoPorId(Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = HibernateEntityManagerHelper.getEntityManager();
            String sql = """
                    SELECT 
                        p.produto_id AS id,
                        p.nome AS nome,
                        p.descricao AS descricao,
                        p.preco AS preco,
                        p.estoque AS estoque,
                        p.imagem_url AS imagemUrl,
                        c.nome AS categoria
                    FROM produtos p
                    LEFT JOIN categorias c ON c.categoria_id = p.categoria_id
                    WHERE p.produto_id = :id
                    """;

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("id", id);

            List<Object[]> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                Object[] row = resultList.get(0);
                return new ProdutoDTO(
                    ((Number) row[0]).longValue(),
                    (String) row[1],
                    (String) row[2],
                    ((Number) row[3]).doubleValue(),
                    ((Number) row[4]).intValue(),
                    (String) row[5],
                    (String) row[6]
                );
            }
            return null;
        } finally {
            if (entityManager != null) {
                HibernateEntityManagerHelper.closeEntityManager();
            }
        }
    }
	
	public Produto findById(Long produtoId) {
		EntityManager entityManager = null;
		try {
			entityManager = HibernateEntityManagerHelper.getEntityManager();
			return entityManager.find(Produto.class, produtoId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (entityManager != null) {
				HibernateEntityManagerHelper.closeEntityManager();
			}
		}
	}
	
	public List<SelectItem> findAllAsSelectItems() {
	    EntityManager entityManager = null;
	    List<SelectItem> options = new ArrayList<>();

	    try {
	        entityManager = HibernateEntityManagerHelper.getEntityManager();
	        TypedQuery<Produto> query = entityManager.createQuery("SELECT p FROM Produto p", Produto.class);
	        List<Produto> produtos = query.getResultList();

	        for (Produto produto : produtos) {
	            // Adiciona cada Categoria como SelectItem (value e label)
	            options.add(new SelectItem(produto.getProdutoId(), produto.getNome()));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (entityManager != null) {
	            HibernateEntityManagerHelper.closeEntityManager();
	        }
	    }

	    return options;
	}
	
}

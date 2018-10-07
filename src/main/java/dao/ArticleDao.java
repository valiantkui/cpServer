package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Article;

@Repository("articleDao")
public interface ArticleDao {

	public int addArticle(Article article);
	public List<Article> findArticleByU_id(String u_id);
	
	public List<Article> findArticleByS_no(String s_no);
	public Article findArticleByContent_link(String contentLink);
	public void deleteArticleByA_no(String a_no);
	
	/**
	 * 根据关键字进行模糊查询
	 * @param searchContent
	 * @return
	 */
	public List<Article> searchArticle(String searchContent);
}

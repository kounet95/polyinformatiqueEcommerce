import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { commandbolg } from '../../../mesApi/commandeBlog';
import { queryblog } from '../../../mesApi/queryBlog';
import { ArticleModel, NewsModel, EventModel } from '../../mesModels/models';

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {

  constructor(private http: HttpClient) {}

  // ------------------ QUERY (lecture) ------------------

  // ARTICLES
  public getAllArticles(): Observable<ArticleModel[]> {
    return this.http.get<ArticleModel[]>(`${queryblog.backend}/query/articles`);
  }

  public getArticle(id: string): Observable<ArticleModel> {
    return this.http.get<ArticleModel>(`${queryblog.backend}/query/articles/${id}`);
  }

  // Streaming (SSE or RxJs, selon backend, ici Observable<ArticleModel>)
  public watchArticle(articleId: string): Observable<ArticleModel> {
    return this.http.get<ArticleModel>(`${queryblog.backend}/query/watch/${articleId}`);
  }

  // NEWS
  public getAllNews(): Observable<NewsModel[]> {
    return this.http.get<NewsModel[]>(`${queryblog.backend}/news`);
  }

  public getNews(id: string): Observable<NewsModel> {
    return this.http.get<NewsModel>(`${queryblog.backend}/news/${id}`);
  }

  // EVENTS
  public getAllEvents(): Observable<EventModel[]> {
    return this.http.get<EventModel[]>(`${queryblog.backend}/events`);
  }

  public getEvent(id: string): Observable<EventModel> {
    return this.http.get<EventModel>(`${queryblog.backend}/events/${id}`);
  }

  // ------------------ COMMAND (écriture) ------------------

  // ARTICLE CRUD (multipart pour creation)
  public createArticle(formData: FormData): Observable<void> {
    return this.http.post<void>(`${commandbolg.backend}/blog/command/create`, formData);
  }

  public updateArticle(id: string, article: ArticleModel): Observable<void> {
    return this.http.put<void>(`${commandbolg.backend}/blog/command/update-article/${id}`, article);
  }

  public deleteArticle(id: string): Observable<void> {
    return this.http.delete<void>(`${commandbolg.backend}/blog/command/delete-article/${id}`);
  }

  // NEWS CRUD (multipart pour creation)
  public createNews(formData: FormData): Observable<void> {
    return this.http.post<void>(`${commandbolg.backend}/blog/command/create-news`, formData);
  }

  public updateNews(id: string, news: NewsModel): Observable<void> {
    return this.http.put<void>(`${commandbolg.backend}/blog/command/update-news/${id}`, news);
  }

  public deleteNews(id: string): Observable<void> {
    return this.http.delete<void>(`${commandbolg.backend}/blog/command/delete-news/${id}`);
  }

  // EVENT CRUD (multipart pour creation)
  public createEvent(formData: FormData): Observable<void> {
    return this.http.post<void>(`${commandbolg.backend}/blog/command/create-event`, formData);
  }

  public updateEvent(id: string, event: EventModel): Observable<void> {
    return this.http.put<void>(`${commandbolg.backend}/blog/command/update-event/${id}`, event);
  }

  public deleteEvent(id: string): Observable<void> {
    return this.http.delete<void>(`${commandbolg.backend}/blog/command/delete-event/${id}`);
  }

  // Upload d'image
  public uploadImage(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${commandbolg.backend}/blog/command/upload-image`, formData, { responseType: 'text' });
  }

  // Récupération du stream d'événements d'un agrégat (admin)
  public getEventsStream(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${commandbolg.backend}/blog/command/events/${aggregateId}`);
  }
}
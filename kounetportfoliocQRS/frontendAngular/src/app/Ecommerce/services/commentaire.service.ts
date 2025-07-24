import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommentModel } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {}

  /**
   * Création d'un commentaire (Aggregate : CommentAggregate, Command : CreateCommentCommand)
   * @param comment CommentDTO
   */
  createComment(comment: CommentModel): Observable<void> {
    return this.http.post<void>(
      `${ecpolyCommand.backend}/comment/command/create`, comment
    );
  }

  /**
   * Suppression d'un commentaire (Aggregate : CommentAggregate, Command : DeleteCommentCommand)
   * @param commentId string
   */
  deleteComment(commentId: string): Observable<void> {
    return this.http.delete<void>(
      `${ecpolyCommand.backend}/comment/command/delete/${commentId}`
    );
  }

  /**
   * Récupérer tous les commentaires (Query)
   */
  getAllComments(): Observable<CommentModel[]> {
    return this.http.get<CommentModel[]>(
      `${ecpolyQuery.backend}/comments`
    );
  }

  /**
   * Récupérer un commentaire par son ID (Query)
   * @param commentId string
   */
  getCommentById(commentId: string): Observable<CommentModel> {
    return this.http.get<CommentModel>(
      `${ecpolyQuery.backend}/comments/${commentId}`
    );
  }

  /**
   * Récupérer les événements d'un commentaire (Event sourcing)
   * @param commentId string
   */
  getCommentEvents(commentId: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${ecpolyCommand.backend}/comment/command/events/${commentId}`
    );
  }

  /**
   * Watch un commentaire en temps réel (SSE)
   * @param commentId string
   */
  watchComment(commentId: string): Observable<CommentModel> {
    // Pour les vrais flux SSE, il faut une implémentation spécifique (EventSource/RxJS)
    // Ici, on retourne simplement un Observable HTTP pour la structure, à adapter selon besoin temps réel.
    return this.http.get<CommentModel>(
      `${ecpolyQuery.backend}/comments/watch/${commentId}`
    );
  }
}
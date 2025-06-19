import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SocialGroupDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class CategoriesocialesService {

  constructor(private http: HttpClient) {}

  /**
   * Création d'un groupe social (Aggregate : SocialGroupAggregate, Command : CreateSocialGroupCommand)
   * @param socialGroup SocialGroupDTO
   */
  createSocialGroup(socialGroup: SocialGroupDTO): Observable<void> {
    return this.http.post<void>(
      `${ecpolyCommand.backend}/social-group/command/create`, socialGroup
    );
  }

  /**
   * Suppression d'un groupe social (Aggregate : SocialGroupAggregate, Command : DeleteSocialGroupCommand)
   * @param socialGroupId string
   */
  deleteSocialGroup(socialGroupId: string): Observable<void> {
    return this.http.delete<void>(
      `${ecpolyCommand.backend}/social-group/command/delete/${socialGroupId}`
    );
  }

  /**
   * Récupérer tous les groupes sociaux (Query)
   */
  getAllSocialGroups(): Observable<SocialGroupDTO[]> {
    return this.http.get<SocialGroupDTO[]>(
      `${ecpolyQuery.backend}/api/socialgroups`
    );
  }

  /**
   * Récupérer un groupe social par son ID (Query)
   * @param socialGroupId string
   */
  getSocialGroupById(socialGroupId: string): Observable<SocialGroupDTO> {
    return this.http.get<SocialGroupDTO>(
      `${ecpolyQuery.backend}/api/socialgroups/${socialGroupId}`
    );
  }
}
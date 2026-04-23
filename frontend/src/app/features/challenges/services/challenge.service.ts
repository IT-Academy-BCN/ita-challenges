import { Injectable } from '@angular/core';
import { IChallenge } from '../models/ichallenge.interface';
import { IChallengeResult } from '../models/ichallenge-result.interface';

@Injectable({
  providedIn: 'root',
})

export class ChallengeService {
  
  create(challenge: IChallenge): IChallengeResult { 
    return {
      id: "1",
      title: challenge.title,
      description:challenge.description,
    }
  }

  loadAll(): IChallengeResult[] {
    return [
      {
        id: "1",
        title:"primero",
        description: "primero d"
      },
      {
        id: "2",
        title:"segundo",
        description: "segundo d"
      },
      {
        id: "3",
        title:"tercero",
        description: "tercero d"
      }
    ]
  }

  update(id: string, challenge: IChallenge): IChallengeResult { 
    return {
      id: id,
      title: challenge.title,
      description:challenge.description,
    }
  }

  delete(id: string) { return }

}

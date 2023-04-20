import { ICondition } from 'app/shared/model/condition.model';

export interface IRiskProfile {
  id?: number;
  totalRiskScore?: number | null;
  lifeStyle?: string;
  conditions?: ICondition[] | null;
}

export const defaultValue: Readonly<IRiskProfile> = {};

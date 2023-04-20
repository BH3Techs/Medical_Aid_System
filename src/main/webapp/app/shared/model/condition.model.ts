import { IRiskProfile } from 'app/shared/model/risk-profile.model';

export interface ICondition {
  id?: number;
  name?: string;
  details?: string;
  riskProfile?: IRiskProfile | null;
}

export const defaultValue: Readonly<ICondition> = {};

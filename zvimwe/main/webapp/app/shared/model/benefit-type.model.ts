import { IBenefit } from 'app/shared/model/benefit.model';
import { IPlanBenefit } from 'app/shared/model/plan-benefit.model';

export interface IBenefitType {
  id?: number;
  name?: string;
  description?: string;
  active?: boolean;
  benefits?: IBenefit[] | null;
  planBenefits?: IPlanBenefit[] | null;
}

export const defaultValue: Readonly<IBenefitType> = {
  active: false,
};

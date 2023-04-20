import { IBenefitLimit } from 'app/shared/model/benefit-limit.model';
import { IPlans } from 'app/shared/model/plans.model';
import { IBenefitType } from 'app/shared/model/benefit-type.model';
import { PeriodUnit } from 'app/shared/model/enumerations/period-unit.model';

export interface IPlanBenefit {
  id?: number;
  name?: string;
  waitingPeriodUnit?: PeriodUnit;
  waitingPeriodValue?: number;
  description?: string;
  active?: boolean;
  benefitLimits?: IBenefitLimit[] | null;
  plans?: IPlans | null;
  benefitType?: IBenefitType | null;
}

export const defaultValue: Readonly<IPlanBenefit> = {
  active: false,
};

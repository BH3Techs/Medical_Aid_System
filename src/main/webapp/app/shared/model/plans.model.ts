import { ICurrency } from 'app/shared/model/currency.model';
import { IPlanBenefit } from 'app/shared/model/plan-benefit.model';
import { IPlanBillingCycle } from 'app/shared/model/plan-billing-cycle.model';
import { IPolicy } from 'app/shared/model/policy.model';
import { IPlanCategory } from 'app/shared/model/plan-category.model';
import { PeriodUnit } from 'app/shared/model/enumerations/period-unit.model';

export interface IPlans {
  id?: number;
  planCode?: string;
  name?: string;
  basePremium?: string | null;
  coverAmount?: string;
  coverPeriodUnit?: PeriodUnit;
  coverPeriodValue?: number;
  active?: boolean;
  currencies?: ICurrency[] | null;
  planBenefits?: IPlanBenefit[] | null;
  planBillingCycles?: IPlanBillingCycle[] | null;
  policies?: IPolicy[] | null;
  planCategory?: IPlanCategory | null;
}

export const defaultValue: Readonly<IPlans> = {
  active: false,
};

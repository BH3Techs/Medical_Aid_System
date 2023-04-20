import { IPlanBillingCycle } from 'app/shared/model/plan-billing-cycle.model';
import { IClaim } from 'app/shared/model/claim.model';
import { INextOfKin } from 'app/shared/model/next-of-kin.model';
import { IPlans } from 'app/shared/model/plans.model';
import { PolicyStatus } from 'app/shared/model/enumerations/policy-status.model';

export interface IPolicy {
  id?: number;
  policyNumber?: string;
  suffix?: string;
  pricingGroup?: string | null;
  nextOfKin?: string | null;
  memberIdentifier?: string | null;
  parentPolicy?: string | null;
  sponsorIdentifier?: string | null;
  sponsorType?: string | null;
  status?: PolicyStatus;
  balance?: number;
  planBillingCycle?: IPlanBillingCycle | null;
  claims?: IClaim[] | null;
  nextOfKins?: INextOfKin[] | null;
  plans?: IPlans[] | null;
}

export const defaultValue: Readonly<IPolicy> = {};

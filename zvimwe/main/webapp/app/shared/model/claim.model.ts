import dayjs from 'dayjs';
import { ITarrifClaim } from 'app/shared/model/tarrif-claim.model';
import { IPolicy } from 'app/shared/model/policy.model';
import { IServiceProvider } from 'app/shared/model/service-provider.model';
import { ClaimStatus } from 'app/shared/model/enumerations/claim-status.model';

export interface IClaim {
  id?: number;
  submissionDate?: string;
  approvalDate?: string | null;
  processingDate?: string | null;
  claimStatus?: ClaimStatus | null;
  diagnosis?: string | null;
  claimant?: string | null;
  relationshipToMember?: string | null;
  tarrifClaims?: ITarrifClaim[] | null;
  policy?: IPolicy | null;
  serviceProvider?: IServiceProvider | null;
}

export const defaultValue: Readonly<IClaim> = {};

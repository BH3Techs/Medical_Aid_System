import planCategory from 'app/entities/plan-category/plan-category.reducer';
import plans from 'app/entities/plans/plans.reducer';
import currency from 'app/entities/currency/currency.reducer';
import planBenefit from 'app/entities/plan-benefit/plan-benefit.reducer';
import benefit from 'app/entities/benefit/benefit.reducer';
import benefitType from 'app/entities/benefit-type/benefit-type.reducer';
import tariff from 'app/entities/tariff/tariff.reducer';
import benefitLimit from 'app/entities/benefit-limit/benefit-limit.reducer';
import benefitLimitType from 'app/entities/benefit-limit-type/benefit-limit-type.reducer';
import benefitClaimTracker from 'app/entities/benefit-claim-tracker/benefit-claim-tracker.reducer';
import policy from 'app/entities/policy/policy.reducer';
import document from 'app/entities/document/document.reducer';
import documentType from 'app/entities/document-type/document-type.reducer';
import individual from 'app/entities/individual/individual.reducer';
import group from 'app/entities/group/group.reducer';
import nextOfKin from 'app/entities/next-of-kin/next-of-kin.reducer';
import contactDetails from 'app/entities/contact-details/contact-details.reducer';
import address from 'app/entities/address/address.reducer';
import bankingDetails from 'app/entities/banking-details/banking-details.reducer';
import wallet from 'app/entities/wallet/wallet.reducer';
import claim from 'app/entities/claim/claim.reducer';
import tarrifClaim from 'app/entities/tarrif-claim/tarrif-claim.reducer';
import serviceProvider from 'app/entities/service-provider/service-provider.reducer';
import invoice from 'app/entities/invoice/invoice.reducer';
import invoiceLine from 'app/entities/invoice-line/invoice-line.reducer';
import riskProfile from 'app/entities/risk-profile/risk-profile.reducer';
import condition from 'app/entities/condition/condition.reducer';
import sponsorAdministration from 'app/entities/sponsor-administration/sponsor-administration.reducer';
import planBillingCycle from 'app/entities/plan-billing-cycle/plan-billing-cycle.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  planCategory,
  plans,
  currency,
  planBenefit,
  benefit,
  benefitType,
  tariff,
  benefitLimit,
  benefitLimitType,
  benefitClaimTracker,
  policy,
  document,
  documentType,
  individual,
  group,
  nextOfKin,
  contactDetails,
  address,
  bankingDetails,
  wallet,
  claim,
  tarrifClaim,
  serviceProvider,
  invoice,
  invoiceLine,
  riskProfile,
  condition,
  sponsorAdministration,
  planBillingCycle,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

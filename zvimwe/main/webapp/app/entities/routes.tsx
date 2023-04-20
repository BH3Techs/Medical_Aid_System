import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PlanCategory from './plan-category';
import Plans from './plans';
import Currency from './currency';
import PlanBenefit from './plan-benefit';
import Benefit from './benefit';
import BenefitType from './benefit-type';
import Tariff from './tariff';
import BenefitLimit from './benefit-limit';
import BenefitLimitType from './benefit-limit-type';
import BenefitClaimTracker from './benefit-claim-tracker';
import Policy from './policy';
import Document from './document';
import DocumentType from './document-type';
import Individual from './individual';
import Group from './group';
import NextOfKin from './next-of-kin';
import ContactDetails from './contact-details';
import Address from './address';
import BankingDetails from './banking-details';
import Wallet from './wallet';
import Claim from './claim';
import TarrifClaim from './tarrif-claim';
import ServiceProvider from './service-provider';
import Invoice from './invoice';
import InvoiceLine from './invoice-line';
import RiskProfile from './risk-profile';
import Condition from './condition';
import SponsorAdministration from './sponsor-administration';
import PlanBillingCycle from './plan-billing-cycle';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="plan-category/*" element={<PlanCategory />} />
        <Route path="plans/*" element={<Plans />} />
        <Route path="currency/*" element={<Currency />} />
        <Route path="plan-benefit/*" element={<PlanBenefit />} />
        <Route path="benefit/*" element={<Benefit />} />
        <Route path="benefit-type/*" element={<BenefitType />} />
        <Route path="tariff/*" element={<Tariff />} />
        <Route path="benefit-limit/*" element={<BenefitLimit />} />
        <Route path="benefit-limit-type/*" element={<BenefitLimitType />} />
        <Route path="benefit-claim-tracker/*" element={<BenefitClaimTracker />} />
        <Route path="policy/*" element={<Policy />} />
        <Route path="document/*" element={<Document />} />
        <Route path="document-type/*" element={<DocumentType />} />
        <Route path="individual/*" element={<Individual />} />
        <Route path="group/*" element={<Group />} />
        <Route path="next-of-kin/*" element={<NextOfKin />} />
        <Route path="contact-details/*" element={<ContactDetails />} />
        <Route path="address/*" element={<Address />} />
        <Route path="banking-details/*" element={<BankingDetails />} />
        <Route path="wallet/*" element={<Wallet />} />
        <Route path="claim/*" element={<Claim />} />
        <Route path="tarrif-claim/*" element={<TarrifClaim />} />
        <Route path="service-provider/*" element={<ServiceProvider />} />
        <Route path="invoice/*" element={<Invoice />} />
        <Route path="invoice-line/*" element={<InvoiceLine />} />
        <Route path="risk-profile/*" element={<RiskProfile />} />
        <Route path="condition/*" element={<Condition />} />
        <Route path="sponsor-administration/*" element={<SponsorAdministration />} />
        <Route path="plan-billing-cycle/*" element={<PlanBillingCycle />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};

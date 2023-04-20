import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/plan-category">
        <Translate contentKey="global.menu.entities.planCategory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/plans">
        <Translate contentKey="global.menu.entities.plans" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/currency">
        <Translate contentKey="global.menu.entities.currency" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/plan-benefit">
        <Translate contentKey="global.menu.entities.planBenefit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/benefit">
        <Translate contentKey="global.menu.entities.benefit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/benefit-type">
        <Translate contentKey="global.menu.entities.benefitType" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tariff">
        <Translate contentKey="global.menu.entities.tariff" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/benefit-limit">
        <Translate contentKey="global.menu.entities.benefitLimit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/benefit-limit-type">
        <Translate contentKey="global.menu.entities.benefitLimitType" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/benefit-claim-tracker">
        <Translate contentKey="global.menu.entities.benefitClaimTracker" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/policy">
        <Translate contentKey="global.menu.entities.policy" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/document">
        <Translate contentKey="global.menu.entities.document" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/document-type">
        <Translate contentKey="global.menu.entities.documentType" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/individual">
        <Translate contentKey="global.menu.entities.individual" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/group">
        <Translate contentKey="global.menu.entities.group" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/next-of-kin">
        <Translate contentKey="global.menu.entities.nextOfKin" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/contact-details">
        <Translate contentKey="global.menu.entities.contactDetails" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/address">
        <Translate contentKey="global.menu.entities.address" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/banking-details">
        <Translate contentKey="global.menu.entities.bankingDetails" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/wallet">
        <Translate contentKey="global.menu.entities.wallet" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/claim">
        <Translate contentKey="global.menu.entities.claim" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tarrif-claim">
        <Translate contentKey="global.menu.entities.tarrifClaim" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/service-provider">
        <Translate contentKey="global.menu.entities.serviceProvider" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/invoice">
        <Translate contentKey="global.menu.entities.invoice" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/invoice-line">
        <Translate contentKey="global.menu.entities.invoiceLine" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/risk-profile">
        <Translate contentKey="global.menu.entities.riskProfile" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/condition">
        <Translate contentKey="global.menu.entities.condition" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sponsor-administration">
        <Translate contentKey="global.menu.entities.sponsorAdministration" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/plan-billing-cycle">
        <Translate contentKey="global.menu.entities.planBillingCycle" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;

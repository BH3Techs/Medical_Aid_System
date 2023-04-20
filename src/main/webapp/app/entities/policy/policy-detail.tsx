import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './policy.reducer';

export const PolicyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const policyEntity = useAppSelector(state => state.policy.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="policyDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.policy.detail.title">Policy</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{policyEntity.id}</dd>
          <dt>
            <span id="policyNumber">
              <Translate contentKey="medicalAidSystemApp.policy.policyNumber">Policy Number</Translate>
            </span>
          </dt>
          <dd>{policyEntity.policyNumber}</dd>
          <dt>
            <span id="suffix">
              <Translate contentKey="medicalAidSystemApp.policy.suffix">Suffix</Translate>
            </span>
          </dt>
          <dd>{policyEntity.suffix}</dd>
          <dt>
            <span id="pricingGroup">
              <Translate contentKey="medicalAidSystemApp.policy.pricingGroup">Pricing Group</Translate>
            </span>
          </dt>
          <dd>{policyEntity.pricingGroup}</dd>
          <dt>
            <span id="nextOfKin">
              <Translate contentKey="medicalAidSystemApp.policy.nextOfKin">Next Of Kin</Translate>
            </span>
          </dt>
          <dd>{policyEntity.nextOfKin}</dd>
          <dt>
            <span id="memberIdentifier">
              <Translate contentKey="medicalAidSystemApp.policy.memberIdentifier">Member Identifier</Translate>
            </span>
          </dt>
          <dd>{policyEntity.memberIdentifier}</dd>
          <dt>
            <span id="parentPolicy">
              <Translate contentKey="medicalAidSystemApp.policy.parentPolicy">Parent Policy</Translate>
            </span>
          </dt>
          <dd>{policyEntity.parentPolicy}</dd>
          <dt>
            <span id="sponsorIdentifier">
              <Translate contentKey="medicalAidSystemApp.policy.sponsorIdentifier">Sponsor Identifier</Translate>
            </span>
          </dt>
          <dd>{policyEntity.sponsorIdentifier}</dd>
          <dt>
            <span id="sponsorType">
              <Translate contentKey="medicalAidSystemApp.policy.sponsorType">Sponsor Type</Translate>
            </span>
          </dt>
          <dd>{policyEntity.sponsorType}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="medicalAidSystemApp.policy.status">Status</Translate>
            </span>
          </dt>
          <dd>{policyEntity.status}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="medicalAidSystemApp.policy.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{policyEntity.balance}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.policy.planBillingCycle">Plan Billing Cycle</Translate>
          </dt>
          <dd>{policyEntity.planBillingCycle ? policyEntity.planBillingCycle.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/policy" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/policy/${policyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PolicyDetail;

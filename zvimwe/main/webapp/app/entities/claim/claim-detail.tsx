import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './claim.reducer';

export const ClaimDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const claimEntity = useAppSelector(state => state.claim.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.claim.detail.title">Claim</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{claimEntity.id}</dd>
          <dt>
            <span id="submissionDate">
              <Translate contentKey="medicalAidSystemApp.claim.submissionDate">Submission Date</Translate>
            </span>
          </dt>
          <dd>
            {claimEntity.submissionDate ? (
              <TextFormat value={claimEntity.submissionDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="approvalDate">
              <Translate contentKey="medicalAidSystemApp.claim.approvalDate">Approval Date</Translate>
            </span>
          </dt>
          <dd>
            {claimEntity.approvalDate ? <TextFormat value={claimEntity.approvalDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="processingDate">
              <Translate contentKey="medicalAidSystemApp.claim.processingDate">Processing Date</Translate>
            </span>
          </dt>
          <dd>
            {claimEntity.processingDate ? (
              <TextFormat value={claimEntity.processingDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="claimStatus">
              <Translate contentKey="medicalAidSystemApp.claim.claimStatus">Claim Status</Translate>
            </span>
          </dt>
          <dd>{claimEntity.claimStatus}</dd>
          <dt>
            <span id="diagnosis">
              <Translate contentKey="medicalAidSystemApp.claim.diagnosis">Diagnosis</Translate>
            </span>
          </dt>
          <dd>{claimEntity.diagnosis}</dd>
          <dt>
            <span id="claimant">
              <Translate contentKey="medicalAidSystemApp.claim.claimant">Claimant</Translate>
            </span>
          </dt>
          <dd>{claimEntity.claimant}</dd>
          <dt>
            <span id="relationshipToMember">
              <Translate contentKey="medicalAidSystemApp.claim.relationshipToMember">Relationship To Member</Translate>
            </span>
          </dt>
          <dd>{claimEntity.relationshipToMember}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.claim.policy">Policy</Translate>
          </dt>
          <dd>{claimEntity.policy ? claimEntity.policy.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.claim.serviceProvider">Service Provider</Translate>
          </dt>
          <dd>{claimEntity.serviceProvider ? claimEntity.serviceProvider.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/claim" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim/${claimEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimDetail;

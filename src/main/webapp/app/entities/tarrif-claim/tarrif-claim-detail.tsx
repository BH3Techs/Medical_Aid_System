import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tarrif-claim.reducer';

export const TarrifClaimDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tarrifClaimEntity = useAppSelector(state => state.tarrifClaim.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tarrifClaimDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.tarrifClaim.detail.title">TarrifClaim</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tarrifClaimEntity.id}</dd>
          <dt>
            <span id="tarrifCode">
              <Translate contentKey="medicalAidSystemApp.tarrifClaim.tarrifCode">Tarrif Code</Translate>
            </span>
          </dt>
          <dd>{tarrifClaimEntity.tarrifCode}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="medicalAidSystemApp.tarrifClaim.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{tarrifClaimEntity.quantity}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="medicalAidSystemApp.tarrifClaim.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{tarrifClaimEntity.amount}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="medicalAidSystemApp.tarrifClaim.description">Description</Translate>
            </span>
          </dt>
          <dd>{tarrifClaimEntity.description}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.tarrifClaim.currency">Currency</Translate>
          </dt>
          <dd>{tarrifClaimEntity.currency ? tarrifClaimEntity.currency.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.tarrifClaim.claim">Claim</Translate>
          </dt>
          <dd>{tarrifClaimEntity.claim ? tarrifClaimEntity.claim.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tarrif-claim" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tarrif-claim/${tarrifClaimEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TarrifClaimDetail;

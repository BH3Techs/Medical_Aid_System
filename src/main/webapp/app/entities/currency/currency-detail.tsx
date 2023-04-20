import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './currency.reducer';

export const CurrencyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const currencyEntity = useAppSelector(state => state.currency.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="currencyDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.currency.detail.title">Currency</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{currencyEntity.id}</dd>
          <dt>
            <span id="currencyName">
              <Translate contentKey="medicalAidSystemApp.currency.currencyName">Currency Name</Translate>
            </span>
          </dt>
          <dd>{currencyEntity.currencyName}</dd>
          <dt>
            <span id="currencyCode">
              <Translate contentKey="medicalAidSystemApp.currency.currencyCode">Currency Code</Translate>
            </span>
          </dt>
          <dd>{currencyEntity.currencyCode}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.currency.active">Active</Translate>
            </span>
          </dt>
          <dd>{currencyEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.currency.plans">Plans</Translate>
          </dt>
          <dd>{currencyEntity.plans ? currencyEntity.plans.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/currency" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/currency/${currencyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CurrencyDetail;

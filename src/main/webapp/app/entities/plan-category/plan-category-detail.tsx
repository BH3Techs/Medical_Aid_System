import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plan-category.reducer';

export const PlanCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const planCategoryEntity = useAppSelector(state => state.planCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="planCategoryDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.planCategory.detail.title">PlanCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{planCategoryEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.planCategory.name">Name</Translate>
            </span>
          </dt>
          <dd>{planCategoryEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="medicalAidSystemApp.planCategory.description">Description</Translate>
            </span>
          </dt>
          <dd>{planCategoryEntity.description}</dd>
          <dt>
            <span id="dateCreated">
              <Translate contentKey="medicalAidSystemApp.planCategory.dateCreated">Date Created</Translate>
            </span>
          </dt>
          <dd>
            {planCategoryEntity.dateCreated ? (
              <TextFormat value={planCategoryEntity.dateCreated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.planCategory.active">Active</Translate>
            </span>
          </dt>
          <dd>{planCategoryEntity.active ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/plan-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plan-category/${planCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlanCategoryDetail;

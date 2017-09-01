/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package io.openshift.launchpad.ui.booster;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.context.UINavigationContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.NavigationResult;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public class ChooseDeploymentTypeStep implements UIWizardStep
{
   @Inject
   @WithAttributes(label = "Deployment type", type = InputType.RADIO, required = true)
   private UISelectOne<DeploymentType> deploymentType;

   @Inject
   @WithAttributes(label = "OpenShift Cluster", required = true)
   private UISelectOne<String> openShiftCluster;

   @Inject
   private MissionControlValidator missionControlValidator;

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      UIContext context = builder.getUIContext();
      if (context.getProvider().isGUI())
      {
         deploymentType.setItemLabelConverter(DeploymentType::getDescription);
      }
      deploymentType.setValueChoices(EnumSet.of(DeploymentType.CD, DeploymentType.ZIP));
      builder.add(deploymentType);
      List<String> openShiftClusters = missionControlValidator.getOpenShiftClusters(builder.getUIContext());
      openShiftCluster.setValueChoices(openShiftClusters);
      if (openShiftClusters.size() > 0)
      {
         openShiftCluster.setDefaultValue(openShiftClusters.get(0));
         if (openShiftClusters.size() > 1)
         {
            builder.add(openShiftCluster);
         }
      }
   }

   @Override
   public NavigationResult next(UINavigationContext context) throws Exception
   {
      Map<Object, Object> attributeMap = context.getUIContext().getAttributeMap();
      attributeMap.put(DeploymentType.class, deploymentType.getValue());
      attributeMap.put("OPENSHIFT_CLUSTER", openShiftCluster.getValue());
      return null;
   }

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.forCommand(getClass()).name("Deployment type")
               .description("Choose the Deployment type for your booster")
               .category(Categories.create("Openshift.io"));
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      return Results.success();
   }
}
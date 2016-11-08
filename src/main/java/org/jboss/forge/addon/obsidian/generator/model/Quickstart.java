/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.obsidian.generator.model;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Quickstart
{
   private final String name;

   public Quickstart(String name)
   {
      this.name = name;
   }

   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   public Set<Pattern> getPatterns()
   {
      return null;
   }
}
